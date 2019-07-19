package net.meteorr.dev.meteorrcomett.server.messaging.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author RedLux
 */
public class MessagingServerSecurityLevelTwo extends MessagingServerSecurityLevel {
    @Override
    public String encode(String raw, String pass) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] passData = MessagingServerSecurityByteRotator.rotateRight(pass.getBytes(StandardCharsets.UTF_8), pass.length());
        SecretKeySpec key = new SecretKeySpec(passData, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(raw.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String decode(String encoded, String pass) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] passData = MessagingServerSecurityByteRotator.rotateRight(pass.getBytes(StandardCharsets.UTF_8), pass.length());
        SecretKeySpec key = new SecretKeySpec(passData, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encoded.getBytes(StandardCharsets.UTF_8))));
    }
}
