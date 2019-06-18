package net.meteorr.dev.meteorrcomett.server;

import net.meteorr.dev.meteorrcomett.server.exception.TerminalNotInitializedException;
import net.meteorr.dev.meteorrcomett.server.exception.TerminalNotRunningException;
import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.terminal.ServerTerminal;
import net.meteorr.dev.meteorrcomett.server.terminal.command.CommandManager;
import net.meteorr.dev.meteorrcomett.server.utils.ExceptionHandler;

/**
 * @author RedLux
 */
public class MeteorrComettServer {

    private final MeteorrComettServer instance;
    private Boolean running;
    private ExceptionHandler exceptionHandler;
    private ServerTerminal serverTerminal;
    private CommandManager commandManager;

    public MeteorrComettServer() {
        instance = this;
        running = false;
        exceptionHandler = null;
        serverTerminal = null;
        commandManager = null;
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

    public void print(MessageLevel level, String... content) {
        try {
            serverTerminal.print(level, content);
        } catch (TerminalNotInitializedException e) {
            this.exceptionHandler.handle(e);
        }
    }

    public void main(String[] args) {
        this.exceptionHandler = new ExceptionHandler(getInstance());
        this.commandManager = new CommandManager(getInstance());

        serverTerminal = new ServerTerminal(getInstance());
        try {
            serverTerminal.init();
        } catch (Exception e) {
            this.exceptionHandler.handle(e);
        }
    }

    public void issueCommand(String command) {
        System.out.print("\rCommand issued: " + command + "\n");
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() throws TerminalNotRunningException {
        getServerTerminal().stop();
        System.out.println("bye!");
    }
}
