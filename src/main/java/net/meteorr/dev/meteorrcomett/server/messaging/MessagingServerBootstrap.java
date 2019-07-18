package net.meteorr.dev.meteorrcomett.server.messaging;

import net.meteorr.dev.meteorrcomett.server.messaging.logging.MessagingLoggerHandler;

import java.io.File;

public class MessagingServerBootstrap {
    private MessagingLoggerHandler messagingLoggerHandler;

    public MessagingServerBootstrap(MessagingLoggerHandler messagingLoggerHandler) {
        this.messagingLoggerHandler = messagingLoggerHandler;
        init();
    }

    public void init() {
        messagingLoggerHandler.logInfo("Initializing the MessagingServer...");
        messagingLoggerHandler.logInfo("Getting directory...");
        File dir = new File("messaging/");
        if (!dir.exists()) {
            messagingLoggerHandler.logInfo("Creating it...");
            dir.mkdir();
            messagingLoggerHandler.logInfo("$GREENCreated!");
        } else messagingLoggerHandler.logInfo("$GREENDirectory exists!");
        messagingLoggerHandler.logInfo("MessagingServer initialized $GREENsuccessfully$RESET!");
    }

    public synchronized void start() {

    }

}
