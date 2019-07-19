package net.meteorr.dev.meteorrcomett.server.messaging.security;

import io.netty.handler.ssl.SslContext;
import net.meteorr.dev.meteorrcomett.server.messaging.MessagingServerBootstrap;
import net.meteorr.dev.meteorrcomett.server.messaging.config.MessagingServerConfig;

import java.io.File;

public class MessagingServerSecurityLoader {
    private MessagingServerBootstrap messagingServerBootstrap;

    public MessagingServerSecurityLoader(MessagingServerBootstrap messagingServerBootstrap) {
        this.messagingServerBootstrap = messagingServerBootstrap;
    }

    public MessagingServerBootstrap getMessagingServerBootstrap() {
        return messagingServerBootstrap;
    }

    public SslContext load(MessagingServerConfig config) throws Exception {
        MessagingServerSecurityContextBuilder builder = new MessagingServerSecurityContextBuilder();
        if (config.isSsl()) {

            return null;
        } else return builder.build();
    }

    public File getFile(String name) {
        File file = new File("messaging/" + name);
        //TODO
        return null;
    }
}
