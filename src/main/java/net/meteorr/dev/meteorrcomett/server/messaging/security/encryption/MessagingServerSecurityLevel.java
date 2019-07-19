package net.meteorr.dev.meteorrcomett.server.messaging.security.encryption;

/**
 * @author RedLux
 */
public abstract class MessagingServerSecurityLevel {
    public abstract String encode(String raw, String pass) throws Exception;

    public abstract String decode(String encoded, String pass) throws Exception;
}
