package net.meteorr.dev.meteorrcomett.server.console.command;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.utils.exception.CommandAlreadyExistException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.CommandAlreadyRegisteredException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ThreadGroupNotInitializedException;
import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author RedLux
 */
public class CommandManager {
    private final MeteorrComettServer instance;
    private List<ComettServerCommand> commands;
    private CommandExecutor commandExecutor;

    public CommandManager(MeteorrComettServer instance) {
        this.instance = instance;
        this.commands = new ArrayList<>();
        this.commandExecutor = null;
    }

    public void initExecutor() throws ThreadGroupNotInitializedException {
        this.commandExecutor = new CommandExecutor(getInstance());
        getInstance().print(MessageLevel.INFO,"Starting executor...");
        this.commandExecutor.start();
        getInstance().print(MessageLevel.INFO,"$GREENExecutor successfully started!");
    }

    public void registerCommand(ComettServerCommand command) {
        getInstance().print(MessageLevel.INFO,"Registering command " + command.getLabel() + " with class " + command.getClass() + "...");
        getCommands().forEach(cmd -> {
            if (cmd.getLabel().equals(command.getLabel())) try {
                throw new CommandAlreadyExistException(command.getLabel());
            } catch (CommandAlreadyExistException e) {
                getInstance().getExceptionHandler().handle(e);
                getInstance().print(MessageLevel.INFO,"$REDFailed to register command " + command.getLabel() + " with class " + command.getClass() + "!");
            }
        });
        if (!commands.contains(command)) {
            commands.add(command);
            getInstance().print(MessageLevel.INFO,"$GREENSuccessfully registered command " + command.getLabel() + " with class " + command.getClass() + "!");
        } else {
            try {
                throw new CommandAlreadyRegisteredException();
            } catch (CommandAlreadyRegisteredException e) {
                getInstance().getExceptionHandler().handle(e);
                getInstance().print(MessageLevel.INFO,"$REDFailed to register command " + command.getLabel() + " with class " + command.getClass() + "!");
            }
        }
    }

    public synchronized void proceed(String input) throws ThreadGroupNotInitializedException, IOException {
        if (input.equals("")) return;
        getInstance().getServerLogger().write(MessageLevel.DEBUG, "Command input: " + input);
        String label = input.contains(" ") ? input.split(" ")[0] : input;
        String[] args = input.contains(" ") ? input.split(label + " ")[1].split(" ") : new String[0];
        AtomicBoolean success = new AtomicBoolean(false);
        getCommands().forEach(command -> {
            if (command.getLabel().equalsIgnoreCase(label)) {
                getCommandExecutor().execute(command, args);
                success.set(true);
            }
        });
        if (!success.get()) getInstance().print(MessageLevel.WARNING, "No command found for : " + label);
        /**if (input.equals("stop")) getInstance().stop();
        if (input.equals("logfile")) {
            getInstance().print(MessageLevel.INFO, "Log file :" + getInstance().getServerLogger().getLogFile().getAbsolutePath());
        }
        if (input.equals("testmsg")) {

            new WaitableInlineThread(getInstance().getThreadGroup(),"TestRun") {

                @Override
                public synchronized void run() {
                    for (int i = 0; i<10; i++) {
                        getInstance().print(MessageLevel.INFO, input, "test: $BG_CYAN" + (i + 1));
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            getInstance().getExceptionHandler().handle(e);
                        }
                    }
                }
            }.start();
        }
        if (input.equals("listthread")) {
            List<Thread> threads = ThreadsUtil.getGroupThreads(getInstance().getThreadGroup());
            final String[] s = {""};
            threads.forEach(thread -> s[0] += "--> " + thread.getName() + " (" + thread.getState() + "): " + thread.getClass().getName() + "\n");
            getInstance().print(MessageLevel.DEBUG, "Il y a actuellement " + threads.size() + " sous-threads: ", s[0]);
        }**/
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public List<ComettServerCommand> getCommands() {
        return commands;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }
}
