package net.meteorr.dev.meteorrcomett.server.console.command;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.utils.annotations.MeteorrComettImportantThread;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ThreadGroupNotInitializedException;

/**
 * @author RedLux
 */
@MeteorrComettImportantThread(name="MeteorrComettServerCommandExecutor")
public class CommandExecutor extends Thread {
    private final MeteorrComettServer instance;
    private ComettServerCommand command;
    private String[] args;
    private boolean running;

    public CommandExecutor(MeteorrComettServer instance) throws ThreadGroupNotInitializedException {
        super(instance.getThreadGroup(), "MeteorrComettServerCommandExecutor");
        this.instance = instance;
        this.command = null;
        this.args = null;
        this.running = false;
    }

    @Override
    public synchronized void start() {
        super.start();
        this.running = true;
    }

    public synchronized ComettServerCommand getCommand() {
        return command;
    }

    public synchronized String[] getArgs() {
        return args;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized MeteorrComettServer getInstance() {
        return instance;
    }

    @Override
    public synchronized void run() {
        super.run();
        try {
            wait();
        } catch (InterruptedException e) {
            getInstance().getExceptionHandler().handle(e);
        }
        do {
            try {
                getCommand().execute(getInstance(), getArgs());
            } catch (Exception e) {
                if (getInstance().isChecked()) getInstance().getExceptionHandler().handle(e);
            }
            this.args = null;
            this.command = null;
            try {
                if (isRunning()) wait();
            } catch (InterruptedException e) {
                getInstance().getExceptionHandler().handle(e);
            }
        } while (isRunning());
    }

    public synchronized void end() {
        this.running = false;
        this.notify();
    }


    public synchronized void execute(ComettServerCommand command, String[] args) {
        this.command = command;
        this.args = args;
        this.notify();
    }
}
