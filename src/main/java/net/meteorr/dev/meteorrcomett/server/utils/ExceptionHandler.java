package net.meteorr.dev.meteorrcomett.server.utils;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.exception.MeteorrComettServerException;
import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author RedLux
 */
public class ExceptionHandler {
    private final MeteorrComettServer instance;

    public ExceptionHandler(MeteorrComettServer instance) {
        this.instance = instance;
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public void handle(Exception e) {
        if (getInstance().getServerTerminal().isInitialized()) {
            if (e instanceof MeteorrComettServerException) {
                getInstance().getServerTerminal().print(((MeteorrComettServerException) e).getLevel(), "Exception Handled: " + e.getMessage() + " {" + e.getClass().getSimpleName() + "}", getStackTrace(e));
            } else getInstance().getServerTerminal().print(MessageLevel.ERROR, "Exception Occured: " + e.getMessage() + " {" + e.getClass().getSimpleName() + "}", getStackTrace(e));
        } else e.printStackTrace();
    }

    private String getStackTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
