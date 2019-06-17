package net.meteorr.dev.meteorrcomett.server;

import net.meteorr.dev.meteorrcomett.server.exception.TerminalAlreadyInitializedException;
import net.meteorr.dev.meteorrcomett.server.exception.TerminalInitializingException;
import net.meteorr.dev.meteorrcomett.server.exception.TerminalNotInitializedException;
import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.terminal.ServerTerminal;
import net.meteorr.dev.meteorrcomett.server.utils.ExceptionHandler;

/**
 * @author RedLux
 */
public class MeteorrComettServer {

    private final MeteorrComettServer instance;
    private Boolean running;
    private ExceptionHandler exceptionHandler;
    private ServerTerminal serverTerminal;

    public MeteorrComettServer() {
        instance = this;
        running = false;
        exceptionHandler = null;
        serverTerminal = null;
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public ServerTerminal getServerTerminal() {
        return this.serverTerminal;
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

        serverTerminal = new ServerTerminal(getInstance());
        try {
            serverTerminal.init();
        } catch (Exception e) {
            this.exceptionHandler.handle(e);
        }

        /**ThreadConsoleReader threadConsoleReader = new ThreadConsoleReader(instance);
         threadConsoleReader.setDaemon(true);
         threadConsoleReader.start();**/
    }

    public void issueCommand(String command) {
        System.out.print("\rCommand issued: " + command + "\n");
    }

    public boolean isRunning() {
        return running;
    }
}
