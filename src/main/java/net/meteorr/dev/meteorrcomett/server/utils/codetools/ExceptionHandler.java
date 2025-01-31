package net.meteorr.dev.meteorrcomett.server.utils.codetools;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.utils.exception.MeteorrComettServerException;
import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

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

    public void handle(Exception e) {
        try {
            if (getInstance().getServerTerminal().isInitialized()) {
                if (e instanceof MeteorrComettServerException) {
                    getInstance().getServerTerminal().print(((MeteorrComettServerException) e).getLevel(), "Exception Handled: " + e.getMessage() + " {" + e.getClass().getSimpleName() + "}", getStackTrace(e));
                } else getInstance().getServerTerminal().print(MessageLevel.ERROR, "Exception Occured: " + e.getMessage() + " {" + e.getClass().getSimpleName() + "}", getStackTrace(e));
            } else e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getStackTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }
}
