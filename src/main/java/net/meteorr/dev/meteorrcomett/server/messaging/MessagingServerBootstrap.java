package net.meteorr.dev.meteorrcomett.server.messaging;

import net.meteorr.dev.meteorrcomett.server.messaging.config.MessagingServerConfig;
import net.meteorr.dev.meteorrcomett.server.messaging.config.MessagingServerConfigLoader;
import net.meteorr.dev.meteorrcomett.server.messaging.logging.MessagingServerLoggerHandler;

import java.io.File;
import java.io.IOException;

public class MessagingServerBootstrap {
    private MessagingServerLoggerHandler messagingServerLoggerHandler;

    public MessagingServerBootstrap(MessagingServerLoggerHandler messagingServerLoggerHandler) {
        this.messagingServerLoggerHandler = messagingServerLoggerHandler;
    }

    public void init() throws IOException {
        messagingServerLoggerHandler.logInfo("Initializing the MessagingServer...");
        messagingServerLoggerHandler.logInfo("Getting directory...");
        File dir = new File("messaging/");
        if (!dir.exists()) {
            messagingServerLoggerHandler.logInfo("Directory doesn't exists, creating it...");
            dir.mkdir();
            messagingServerLoggerHandler.logInfo("$GREENCreated!");
        } else messagingServerLoggerHandler.logInfo("$GREENDirectory exists!");
        MessagingServerConfig config = new MessagingServerConfigLoader(this).load();
        messagingServerLoggerHandler.logDebug("[Config info] Hostname: " + config.getHostname());
        messagingServerLoggerHandler.logDebug("[Config info] Port: " + config.getPort());
        messagingServerLoggerHandler.logDebug("[Config info] Ssl: " + config.isSsl());
        messagingServerLoggerHandler.logDebug("[Config info] Code: " + config.getCode());
        messagingServerLoggerHandler.logDebug("[Config info] Certfile: " + config.getCertfile());
        messagingServerLoggerHandler.logDebug("[Config info] Keyfile: " + config.getKeyfile());
        messagingServerLoggerHandler.logDebug("[Config info] Passfile: " + config.getPassfile());
        messagingServerLoggerHandler.logInfo("MessagingServer initialized $GREENsuccessfully$RESET!");
    }

    public synchronized void start() {

    }

    public MessagingServerLoggerHandler getMessagingServerLoggerHandler() {
        return messagingServerLoggerHandler;
    }
}
