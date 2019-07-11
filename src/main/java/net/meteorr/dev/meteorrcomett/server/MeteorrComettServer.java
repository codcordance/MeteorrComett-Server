package net.meteorr.dev.meteorrcomett.server;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.console.command.ComettServerCommand;
import net.meteorr.dev.meteorrcomett.server.console.command.CommandManager;
import net.meteorr.dev.meteorrcomett.server.console.logger.ServerLogger;
import net.meteorr.dev.meteorrcomett.server.console.terminal.ServerTerminal;
import net.meteorr.dev.meteorrcomett.server.console.terminal.TerminalReader;
import net.meteorr.dev.meteorrcomett.server.messaging.MessagingEncryptionSetup;
import net.meteorr.dev.meteorrcomett.server.utils.ReflectionUtils;
import net.meteorr.dev.meteorrcomett.server.utils.ThreadsUtils;
import net.meteorr.dev.meteorrcomett.server.utils.annotations.MeteorrComettImportantThread;
import net.meteorr.dev.meteorrcomett.server.utils.annotations.MeteorrComettWaitableThread;
import net.meteorr.dev.meteorrcomett.server.utils.codetools.ComettRunnable;
import net.meteorr.dev.meteorrcomett.server.utils.codetools.ExceptionHandler;
import net.meteorr.dev.meteorrcomett.server.utils.codetools.WaitableInlineThread;
import net.meteorr.dev.meteorrcomett.server.utils.exception.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author RedLux
 *
 * Classe principale du serveur MeteorrComett
 */
public class MeteorrComettServer {

    private final MeteorrComettServer instance;
    private Boolean running;
    private ExceptionHandler exceptionHandler;
    private ServerTerminal serverTerminal;
    private CommandManager commandManager;
    private ThreadGroup threadGroup;
    private ServerLogger serverLogger;
    private Boolean checked;

    public MeteorrComettServer() {
        instance = this;
        running = false;
        exceptionHandler = null;
        serverTerminal = null;
        commandManager = null;
        threadGroup = null;
        checked = false;
    }

    public synchronized void start(List<String> args) throws InterruptedException, ThreadGroupNotInitializedException, IOException, GzipIOException, TerminalNotRunningException {
        this.exceptionHandler = new ExceptionHandler(getInstance());
        this.serverTerminal = new ServerTerminal(getInstance());
        this.threadGroup = null;
        try {
            getServerTerminal().init();
        } catch (Exception e) {
            this.getExceptionHandler().handle(e);
        }
        initThreadGroup();
        initServerLogger();
        initCommandManager();
        initCommandExecutor();
        initCommands();
        initTerminalReader();
        if (!args.contains("--nocheck")) {
            checked = false;
            print(MessageLevel.INFO, "You're running the MeteorrComett $PURPLESERVER");
            print(MessageLevel.INFO, "Please enter 'yes' below if you agree and want to run it or 'no' otherwise.");
            wait();
        } else checked = true;
        if (!checked) {
            print(MessageLevel.WARNING, "The input was not 'no', stopping program...");
            stop();
        } else print(MessageLevel.INFO, "The input was not 'yes', running program!");
        MessagingEncryptionSetup.main(new String[0]);
    }

    public synchronized void checkconsume(boolean result) {
        this.checked = result;
        notify();
    }

    public void print(MessageLevel level, String... content) {
        try {
            getServerTerminal().print(level, content);
        } catch (Exception e) {
            this.getExceptionHandler().handle(e);
        }
    }

    private void initComponent(String componentName, ComettRunnable lambdainit) {
        getInstance().print(MessageLevel.INFO,"Initializing " + componentName + "...");
        try {
            lambdainit.run(getInstance());
        } catch (Exception e) {
            try {
                getInstance().print(MessageLevel.INFO,componentName + " initialization $REDfailed$RESET!");
                throw new ComponentFailedToInitializeException(e);
            } catch (ComponentFailedToInitializeException ex) {
                ex.printStackTrace();
            }
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            getExceptionHandler().handle(e);
        }
        getInstance().print(MessageLevel.INFO,componentName + " initialization $GREENsucceed$RESET!");

    }

    private void initCommandManager() {
        initComponent("CommandManager", () -> this.commandManager = new CommandManager(getInstance()));
    }

    private void initCommandExecutor() {
        initComponent("CommandExecutor", () -> this.getCommandManager().initExecutor());
    }

    private void initServerLogger() {
        initComponent("ServerLogger", () -> this.serverLogger = new ServerLogger(getInstance()));
    }

    private void initThreadGroup() {
        initComponent("ThreadGroup", () -> this.threadGroup = new ThreadGroup("MeteorrComettServer"));
    }

    private void initTerminalReader() {
        initComponent("TerminalReader", () -> {
            getInstance().print(MessageLevel.INFO, "Setting terminal reader...");
            getServerTerminal().setTerminalReader(new TerminalReader(getInstance(), getServerTerminal().getTerminal()));
            getInstance().print(MessageLevel.INFO, "Initializing terminal reader...");
            TimeUnit.SECONDS.sleep(2);
            getServerTerminal().initReader();
        });
    }

    private void initCommands() {
        initComponent("Commands", () -> {
            Arrays.asList(ReflectionUtils.getClasses("net.meteorr.dev.meteorrcomett.server.commands")).forEach(aClass -> {
                try {
                    getCommandManager().registerCommand((ComettServerCommand) aClass.getConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    try {
                        throw new FailedToReflectCommandException(e);
                    } catch (FailedToReflectCommandException ex) {
                        getExceptionHandler().handle(e);
                    }
                }
            });
        });
    }

    public synchronized void stop() throws TerminalNotRunningException, InterruptedException, ThreadGroupNotInitializedException, IOException, GzipIOException {
        getInstance().print(MessageLevel.INFO,"Stopping...");
        getServerTerminal().stopReader();
        getInstance().print(MessageLevel.INFO,"Ending $YELLOWCommandExecutor$RESET...");
        getCommandManager().getCommandExecutor().end();
        getInstance().print(MessageLevel.INFO,"Ended $GREENCommandExecutor$RESET!");
        getInstance().print(MessageLevel.INFO,"Intterupting non-importants threads...");
        List<Thread> threads = ThreadsUtils.getGroupThreads(getInstance().getThreadGroup());
        threads.forEach(thread -> {
            if (!thread.getClass().isAnnotationPresent(MeteorrComettImportantThread.class)) {
                if (thread.getClass().isAnnotationPresent(MeteorrComettWaitableThread.class) || thread instanceof WaitableInlineThread) {
                    long timeout = (thread instanceof WaitableInlineThread ? WaitableInlineThread.class : thread.getClass()).getAnnotation(MeteorrComettWaitableThread.class).timeout();
                    try {
                        getInstance().print(MessageLevel.INFO,"Joinin thread $GREEN" + thread.getClass().getName() + "$RESET for " + timeout + " miliseconds...");
                        long l = 0;
                        while (l < timeout && thread.isAlive()) {
                            wait(1);
                            l++;
                        }
                        if (!thread.isAlive()) getInstance().print(MessageLevel.INFO,"Thread $GREEN" + thread.getClass().getName() + "$RESET joined and died!");
                        else {
                            getInstance().print(MessageLevel.INFO,"Intterupting thread $YELLOW" + thread.getClass().getName() + "$RESET...");
                            thread.interrupt();
                            getInstance().print(MessageLevel.INFO,"Intterupted thread $GREEN" + thread.getClass().getName() + "$RESET!");
                        }
                    } catch (InterruptedException ignored) {}
                } else {
                    getInstance().print(MessageLevel.INFO,"Intterupting thread $YELLOW" + thread.getClass().getName() + "$RESET...");
                    thread.interrupt();
                    getInstance().print(MessageLevel.INFO,"Intterupted thread $GREEN" + thread.getClass().getName() + "$RESET!");
                }
                if (thread.isAlive()) {
                    getInstance().print(MessageLevel.CRITICAL,"FORCING THREAD " + thread.getClass().getName() + " TO DIE $RESET!");
                    thread.stop();
                    getInstance().print(MessageLevel.WARNING,"Forced thread $YELLOW" + thread.getClass().getName() + " to die $RESET!");
                }
            }
        });
        getInstance().print(MessageLevel.INFO,"$GREENIntterupted all non important threads!");
        getInstance().print(MessageLevel.INFO,"Ending $YELLOWServerLogger$RESET...");
        getServerLogger().stop();
        getInstance().print(MessageLevel.INFO,"Ended $GREENServerLogger$RESET!");
        getServerTerminal().stop();
        System.out.println("Stopped.");
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public ServerTerminal getServerTerminal() {
        return this.serverTerminal;
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    public boolean isRunning() {
        return this.running;
    }

    public ThreadGroup getThreadGroup() throws ThreadGroupNotInitializedException {
        if (this.threadGroup == null) throw new ThreadGroupNotInitializedException();
        return this.threadGroup;
    }

    public ServerLogger getServerLogger() {
        return this.serverLogger;
    }

    public boolean isChecked() {
        return this.checked;
    }
}
