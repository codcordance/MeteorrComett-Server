package net.meteorr.dev.meteorrcomett.server;

import net.meteorr.dev.meteorrcomett.server.utils.WaitableInlineThread;
import net.meteorr.dev.meteorrcomett.server.utils.annotations.MeteorrComettWaitableThread;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ComponentFailedToInitializeException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.TerminalNotInitializedException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.TerminalNotRunningException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ThreadGroupInitializedException;
import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.terminal.ServerTerminal;
import net.meteorr.dev.meteorrcomett.server.terminal.TerminalReader;
import net.meteorr.dev.meteorrcomett.server.utils.ComettRunnable;
import net.meteorr.dev.meteorrcomett.server.terminal.command.CommandManager;
import net.meteorr.dev.meteorrcomett.server.utils.ExceptionHandler;
import net.meteorr.dev.meteorrcomett.server.utils.annotations.MeteorrComettImportantThread;
import net.meteorr.dev.meteorrcomett.server.utils.ThreadsUtil;

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

    public MeteorrComettServer() {
        instance = this;
        running = false;
        exceptionHandler = null;
        serverTerminal = null;
        commandManager = null;
        threadGroup = null;
    }

    public void start(List<String> args) {
        this.exceptionHandler = new ExceptionHandler(getInstance());
        this.serverTerminal = new ServerTerminal(getInstance());
        this.threadGroup = null;
        try {
            getServerTerminal().init();
        } catch (Exception e) {
            this.getExceptionHandler().handle(e);
        }
        initCommandManager();
        initThreadGroup();
        initTerminalReader();
        if (!args.contains("--nocheck")) {
            print(MessageLevel.INFO, "You're running the MeteorrComett $PURPLESERVER");
        }
    }

    public synchronized void stop() throws TerminalNotRunningException, InterruptedException, ThreadGroupInitializedException {
        getInstance().print(MessageLevel.INFO,"Stopping...");
        getInstance().print(MessageLevel.INFO,"Intterupting non-importants threads...");
        List<Thread> threads = ThreadsUtil.getGroupThreads(getInstance().getThreadGroup());
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
        getServerTerminal().stop();
        System.out.println("bye!");
    }

    public void print(MessageLevel level, String... content) {
        try {
            getServerTerminal().print(level, content);
        } catch (TerminalNotInitializedException e) {
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
        getInstance().print(MessageLevel.INFO,componentName + " initialization $GREENsucceed$RESET!");

    }

    private void initCommandManager() {
        initComponent("CommandManager", () -> this.commandManager = new CommandManager(getInstance()));
    }

    private void initThreadGroup() {
        initComponent("ThreadGroup", () -> this.threadGroup = new ThreadGroup("MeteorrComettServer"));
    }

    private void initTerminalReader() {
        initComponent("TerminalReader", () -> {
            getServerTerminal().setTerminalReader(new TerminalReader(getInstance(), getServerTerminal().getTerminal()));
            TimeUnit.SECONDS.sleep(2);
            getServerTerminal().initReader();
        });
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

    public ThreadGroup getThreadGroup() throws ThreadGroupInitializedException {
        if (this.threadGroup == null) throw new ThreadGroupInitializedException();
        return this.threadGroup;
    }
}
