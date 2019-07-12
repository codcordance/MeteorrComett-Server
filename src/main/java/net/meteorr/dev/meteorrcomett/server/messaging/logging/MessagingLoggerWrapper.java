package net.meteorr.dev.meteorrcomett.server.messaging.logging;

import java.util.logging.Logger;

/**
 * @author RedLux
 */
public class MessagingLoggerWrapper {
    public static final String NAME = "MeteorrComettMessagingServerLogger";
    private MessagingLoggerHandler messagingLoggerHandler;
    private Logger logger;

    public MessagingLoggerWrapper(MessagingLoggerHandler messagingLoggerHandler) {
        this.messagingLoggerHandler = messagingLoggerHandler;
        init();
    }

    private void init() {
        this.logger = Logger.getLogger(NAME);
        logger.addHandler(getMessagingLoggerHandler());
    }

    private MessagingLoggerHandler getMessagingLoggerHandler() {
        return messagingLoggerHandler;
    }

    public Logger getLogger() {
        return logger;
    }
}
