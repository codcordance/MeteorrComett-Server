package net.meteorr.dev.meteorrcomett.server.messaging.security.encryption;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author RedLux
 */
public class MessagingServerSecurityLevelOne extends MessagingServerSecurityLevel {
    @Override
    public String encode(String raw, String pass) {
        return Base64.getEncoder().encodeToString(MessagingServerSecurityByteRotator.rotateRight((raw + raw).getBytes(StandardCharsets.UTF_8), pass.length()));
    }

    @Override
    public String decode(String encoded, String pass) {
        String s = new String(MessagingServerSecurityByteRotator.rotateLeft(Base64.getDecoder().decode(encoded), pass.length()));
        return s.substring(0, s.length() / 2);
    }
}
