package net.meteorr.dev.meteorrcomett.server.messaging.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author RedLux
 */
public abstract class MessagingServerLoggerHandler extends Handler {
    @Override
    public void publish(LogRecord record) {
        String message = "(Messaging) " + record.getMessage();
        if (record.getLevel() == Level.CONFIG || record.getLevel() == Level.FINE || record.getLevel() == Level.FINER || record.getLevel() == Level.FINEST)
            logDebug(message);
        else if (record.getLevel() == Level.WARNING) logWarning(message);
        else if (record.getLevel() == Level.SEVERE) logCritical(message);
        else if (record.getLevel() == Level.INFO) logInfo(message);
        else logError(message);
    }

    public abstract void logInfo(String message);

    public abstract void logWarning(String message);

    public abstract void logError(String message);

    public abstract void logCritical(String message);

    public abstract void logDebug(String message);

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
}
