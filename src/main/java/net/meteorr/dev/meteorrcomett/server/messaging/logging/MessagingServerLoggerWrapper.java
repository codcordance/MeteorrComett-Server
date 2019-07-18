package net.meteorr.dev.meteorrcomett.server.messaging.logging;

import java.util.logging.Logger;

/**
 * @author RedLux
 */
public class MessagingServerLoggerWrapper {
    public static final String NAME = "MeteorrComettMessagingServerLogger";
    private MessagingServerLoggerHandler messagingServerLoggerHandler;
    private Logger logger;

    public MessagingServerLoggerWrapper(MessagingServerLoggerHandler messagingServerLoggerHandler) {
        this.messagingServerLoggerHandler = messagingServerLoggerHandler;
        init();
    }

    private void init() {
        this.logger = Logger.getLogger(NAME);
        logger.addHandler(getMessagingServerLoggerHandler());
    }

    private MessagingServerLoggerHandler getMessagingServerLoggerHandler() {
        return messagingServerLoggerHandler;
    }

    public Logger getLogger() {
        return logger;
    }
}
