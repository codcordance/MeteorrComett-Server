package net.meteorr.dev.meteorrcomett.server.console.command;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.utils.WaitableInlineThread;
import net.meteorr.dev.meteorrcomett.server.utils.exception.TerminalNotRunningException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ThreadGroupNotInitializedException;
import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.utils.ThreadsUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author RedSpri
 */
public class CommandManager {
    private final MeteorrComettServer instance;
    private List<ComettServerCommand> commands;
    //TODO private final CommandExecutor

    public CommandManager(MeteorrComettServer instance) {
        this.instance = instance;
        this.commands = new ArrayList<>();
    }

    public void registerCommand(ComettServerCommand command) {
        if (!commands.contains(command)) commands.add(command);
    }

    public synchronized void proceed(String input) throws TerminalNotRunningException, InterruptedException, ThreadGroupNotInitializedException, IOException {
        getInstance().getServerLogger().write(MessageLevel.DEBUG, "Command input: " + input);

        if (input.equals("stop")) getInstance().stop();
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
        }

    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }


}
