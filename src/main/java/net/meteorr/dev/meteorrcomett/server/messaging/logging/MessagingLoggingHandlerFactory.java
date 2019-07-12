package net.meteorr.dev.meteorrcomett.server.messaging.logging;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author RedLux
 */
public class MessagingLoggingHandlerFactory {
    private static volatile LoggingHandler loggingHandler = null;

    public static synchronized LoggingHandler getLoggingHandler(MessagingLoggerHandler handler) {
        if (loggingHandler == null) buildLoggingHandler(handler);
        return loggingHandler;
    }

    private static void buildLoggingHandler(MessagingLoggerHandler handler) {
        new MessagingLoggerWrapper(handler);
        loggingHandler = new LoggingHandler(MessagingLoggerWrapper.NAME, LogLevel.INFO);
        //loggingHandler.logger =
    }
}
