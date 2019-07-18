package net.meteorr.dev.meteorrcomett.server;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.console.command.MeteorrComettServerCommand;
import net.meteorr.dev.meteorrcomett.server.console.command.MeteorrComettServerCommandManager;
import net.meteorr.dev.meteorrcomett.server.console.logger.MeteorrComettServerLogger;
import net.meteorr.dev.meteorrcomett.server.console.terminal.MeteorrComettServerTerminal;
import net.meteorr.dev.meteorrcomett.server.console.terminal.MeteorrComettServerTerminalReader;
import net.meteorr.dev.meteorrcomett.server.messaging.MessagingServerBootstrap;
import net.meteorr.dev.meteorrcomett.server.messaging.logging.MessagingServerLoggerHandler;
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
@SuppressWarnings({"WeakerAccess", "unused"})
public class MeteorrComettServer {

    private final MeteorrComettServer instance;
    private Boolean running;
    private ExceptionHandler exceptionHandler;
    private MeteorrComettServerTerminal serverTerminal;
    private MeteorrComettServerCommandManager commandManager;
    private ThreadGroup threadGroup;
    private MeteorrComettServerLogger serverLogger;
    private MessagingServerLoggerHandler messagingLoggerImplementation;
    private Boolean checked;

    public MeteorrComettServer() {
        instance = this;
        running = false;
        exceptionHandler = null;
        serverTerminal = null;
        commandManager = null;
        threadGroup = null;
        checked = false;
        messagingLoggerImplementation = null;
    }

    synchronized void start(List<String> args) throws InterruptedException, ThreadGroupNotInitializedException, IOException, GzipIOException, TerminalNotRunningException {
        this.exceptionHandler = new ExceptionHandler(getInstance());
        this.serverTerminal = new MeteorrComettServerTerminal(getInstance());
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
        initMessagingLoggerImplementation();
        print(MessageLevel.INFO, "Initializing the MessagingServer...");
        MessagingServerBootstrap messagingServerBootstrap = new MessagingServerBootstrap(getMessagingLoggerImplementation());
        try {
            messagingServerBootstrap.init();
        } catch (Exception e) {
            getExceptionHandler().handle(e);
            print(MessageLevel.CRITICAL, "Error during MessaingServer initialization, stopping...");
            stop();
        }
        getInstance().print(MessageLevel.INFO, "MessagingServer initialization $GREENsucceed$RESET!");
        print(MessageLevel.INFO, "Sarting the MessagingServer...");
        try {
            messagingServerBootstrap.start();
        } catch (Exception e) {
            getExceptionHandler().handle(e);
            print(MessageLevel.CRITICAL, "Error during MessaingServer start, stopping...");
            stop();
        }
        getInstance().print(MessageLevel.INFO, "MessagingServer started $GREENsuccessfully$RESET!");
        running = true;
        //MessagingEncryptionSetup.main(getMessagingLoggerImplementation());
    }

    public synchronized void checkconsume(boolean result) {
        this.checked = result;
        notify();
    }

    private void initCommandManager() {
        initComponent("MeteorrComettServerCommandManager", () -> this.commandManager = new MeteorrComettServerCommandManager(getInstance()));
    }

    private void initCommandExecutor() {
        initComponent("MeteorrComettServerCommandExecutor", () -> this.getCommandManager().initExecutor());
    }

    private void initServerLogger() {
        initComponent("MeteorrComettServerLogger", () -> this.serverLogger = new MeteorrComettServerLogger(getInstance()));
    }

    private void initThreadGroup() {
        initComponent("MeteorrComettServerThreadGroup", () -> this.threadGroup = new ThreadGroup("MeteorrComettServer"));
    }

    private void initMessagingLoggerImplementation() {
        initComponent("MeteorrComettServerMessagingServerLoggerImplementation", () -> this.messagingLoggerImplementation = new MeteorrComettServerMessagingServerLoggerImplementation(getInstance()));
    }

    private void initTerminalReader() {
        initComponent("MeteorrComettServerTerminalReader", () -> {
            getInstance().print(MessageLevel.INFO, "Setting terminal reader...");
            getServerTerminal().setMeteorrComettServerTerminalReader(new MeteorrComettServerTerminalReader(getInstance(), getServerTerminal().getTerminal()));
            getInstance().print(MessageLevel.INFO, "Initializing terminal reader...");
            TimeUnit.SECONDS.sleep(2);
            getServerTerminal().initReader();
        });
    }

    @SuppressWarnings("unchecked")
    private void initCommands() {
        initComponent("MeteorrComettServerCommands", () -> Arrays.asList(ReflectionUtils.getClasses("net.meteorr.dev.meteorrcomett.server.commands")).forEach(aClass -> {
            try {
                getCommandManager().registerCommand((MeteorrComettServerCommand) aClass.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                try {
                    throw new FailedToReflectCommandException(e);
                } catch (FailedToReflectCommandException ex) {
                    getExceptionHandler().handle(e);
                }
            }
        }));
    }

    private void initComponent(String componentName, ComettRunnable lambdainit) {
        getInstance().print(MessageLevel.INFO, "Initializing " + componentName + "...");
        try {
            lambdainit.run(getInstance());
        } catch (Exception e) {
            try {
                getInstance().print(MessageLevel.INFO, componentName + " initialization $REDfailed$RESET!");
                throw new ComponentFailedToInitializeException(e);
            } catch (ComponentFailedToInitializeException ex) {
                getExceptionHandler().handle(ex);
            }
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            getExceptionHandler().handle(e);
        }
        getInstance().print(MessageLevel.INFO, componentName + " initialization $GREENsucceed$RESET!");

    }

    @SuppressWarnings("deprecation")
    public synchronized void stop() throws TerminalNotRunningException, InterruptedException, ThreadGroupNotInitializedException, IOException, GzipIOException {
        running = false;
        getInstance().print(MessageLevel.INFO,"Stopping...");
        getServerTerminal().stopReader();
        /*Thread t = MessagingEncryptionSetup.t;
        try {
            Method d = t.getClass().getDeclaredMethod("customstop");
            d.setAccessible(true);
            d.invoke(t);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }*/
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
        getMeteorrComettServerLogger().stop();
        getInstance().print(MessageLevel.INFO,"Ended $GREENServerLogger$RESET!");
        getServerTerminal().stop();
        System.out.println("Stopped.");
    }

    public void print(MessageLevel level, String... content) {
        try {
            getServerTerminal().print(level, content);
        } catch (Exception e) {
            this.getExceptionHandler().handle(e);
        }
    }

    public MeteorrComettServerCommandManager getCommandManager() {
        return this.commandManager;
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public MeteorrComettServerTerminal getServerTerminal() {
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

    public MeteorrComettServerLogger getMeteorrComettServerLogger() {
        return this.serverLogger;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public MessagingServerLoggerHandler getMessagingLoggerImplementation() {
        return this.messagingLoggerImplementation;
    }
}
