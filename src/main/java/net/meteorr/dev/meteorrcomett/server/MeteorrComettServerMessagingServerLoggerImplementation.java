package net.meteorr.dev.meteorrcomett.server;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.messaging.logging.MessagingServerLoggerHandler;

public class MeteorrComettServerMessagingServerLoggerImplementation extends MessagingServerLoggerHandler {
    private MeteorrComettServer instance;

    public MeteorrComettServerMessagingServerLoggerImplementation(MeteorrComettServer instance) {
        this.instance = instance;
    }


    @Override
    public void logInfo(String message) {
        getInstance().print(MessageLevel.INFO, message);
    }

    @Override
    public void logWarning(String message) {
        getInstance().print(MessageLevel.WARNING, message);
    }

    @Override
    public void logError(String message) {
        getInstance().print(MessageLevel.ERROR, message);
    }

    @Override
    public void logCritical(String message) {
        getInstance().print(MessageLevel.CRITICAL, message);
    }

    @Override
    public void logDebug(String message) {
        getInstance().print(MessageLevel.DEBUG, message);
    }

    public MeteorrComettServer getInstance() {
        return instance;
    }
}
