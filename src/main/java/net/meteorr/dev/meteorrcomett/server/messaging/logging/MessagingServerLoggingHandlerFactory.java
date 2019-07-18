package net.meteorr.dev.meteorrcomett.server.messaging.logging;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author RedLux
 */
public class MessagingServerLoggingHandlerFactory {
    private static volatile LoggingHandler loggingHandler = null;

    public static synchronized LoggingHandler getLoggingHandler(MessagingServerLoggerHandler handler) {
        if (loggingHandler == null) buildLoggingHandler(handler);
        return loggingHandler;
    }

    private static void buildLoggingHandler(MessagingServerLoggerHandler handler) {
        new MessagingServerLoggerWrapper(handler);
        loggingHandler = new LoggingHandler(MessagingServerLoggerWrapper.NAME, LogLevel.INFO);
    }
}
