package net.meteorr.dev.meteorrcomett.server.messaging.security;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author RedLux
 */
public class MessagingServerSecurityProtocol {
    private MessagingServerSecurityLevelOne serverSecurityLevelOne;
    private MessagingServerSecurityLevelTwo serverSecurityLevelTwo;
    private MessagingServerSecurityLevelThree serverSecurityLevelThree;

    public MessagingServerSecurityProtocol() {
        this.serverSecurityLevelOne = new MessagingServerSecurityLevelOne();
        this.serverSecurityLevelTwo = new MessagingServerSecurityLevelTwo();
        this.serverSecurityLevelThree = new MessagingServerSecurityLevelThree();
    }

    public String encodePassword(String password, String secondpassword) {
        return getServerSecurityLevelOne().encode(password, secondpassword);
    }

    public String decodePassword(String password, String secondpassword) {
        return getServerSecurityLevelOne().decode(password, secondpassword);
    }

    public String encodeString(String raw, String password) throws Exception {
        String s = getServerSecurityLevelOne().encode(getServerSecurityLevelTwo().encode(getServerSecurityLevelThree().encode(raw, password), password), password);
        String toput = Base64.getEncoder().encodeToString(MessagingServerSecurityByteRotator.rotateLeft((s + s + s + s).getBytes(), password.length())).replace("\n", "|newline|");
        Pattern p = Pattern.compile("(.{" + 80 + "})", Pattern.DOTALL);
        Matcher m = p.matcher(toput);
        return "================ METEORR COMETT SERVER MESSAGING SERVER CRYPTED ================\n" + m.replaceAll("$1\n") + "\n================ METEORR COMETT SERVER MESSAGING SERVER CRYPTED ================";
    }

    public String decodeString(String raw, String password) throws Exception {
        String s = new String(MessagingServerSecurityByteRotator.rotateRight(Base64.getDecoder().decode(raw.replace("================ METEORR COMETT SERVER MESSAGING SERVER CRYPTED ================", "").replace("\n", "").replace("|newline|", "\n")), password.length()));
        return getServerSecurityLevelThree().decode(getServerSecurityLevelTwo().decode(getServerSecurityLevelOne().decode(s.substring(0, s.length() / 4), password), password), password);
    }

    public MessagingServerSecurityLevelOne getServerSecurityLevelOne() {
        return serverSecurityLevelOne;
    }

    public MessagingServerSecurityLevelTwo getServerSecurityLevelTwo() {
        return serverSecurityLevelTwo;
    }

    public MessagingServerSecurityLevelThree getServerSecurityLevelThree() {
        return serverSecurityLevelThree;
    }

    /* == Exemple

        MessagingServerSecurityProtocol protocol = new MessagingServerSecurityProtocol();
        String password = "mdp1pluslong";
        String secondpassword = "mdp2";
        String raw = "exemple";
        String encodedpass = protocol.encodePassword(password, secondpassword);
        System.out.println("===> " + encodedpass);
        String encoded = protocol.encodeString(raw, encodedpass);
        System.out.println(encoded);
        String decoded = protocol.decodeString(encoded, encodedpass);
        System.out.println(decoded);

    */
}
