package net.meteorr.dev.meteorrcomett.server.messaging.config;

/**
 * @author RedLux
 */
public class MessagingServerDefaultConfig extends MessagingServerConfig {
    public MessagingServerDefaultConfig() {
        super("0.0.0.0", 570, false, "code", "secondcode", "cert.pem", "key.pem", "pass");
    }
}
