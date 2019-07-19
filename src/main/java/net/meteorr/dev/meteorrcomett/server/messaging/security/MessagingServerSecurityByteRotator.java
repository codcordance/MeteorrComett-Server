package net.meteorr.dev.meteorrcomett.server.messaging.security;

/**
 * @author RedLux
 */
public class MessagingServerSecurityByteRotator {
    public static byte rotateRight(byte byteValue, int rotateValue) {
        rotateValue = correctRotation(rotateValue);
        return (byte) (((byteValue & 0xff) >>> rotateValue) | ((byteValue & 0xff) << (8 - rotateValue)));
    }

    public static byte rotateLeft(byte byteValue, int rotateValue) {
        rotateValue = correctRotation(rotateValue);
        return (byte) (((byteValue & 0xff) << rotateValue) | ((byteValue & 0xff) >>> (8 - rotateValue)));
    }

    public static byte[] rotateRight(byte[] byteValues, int rotateValue) {
        byte[] result = new byte[byteValues.length];
        for (int i = 0; i < byteValues.length; i++) result[i] = rotateRight(byteValues[i], rotateValue);
        return result;
    }

    public static byte[] rotateLeft(byte[] byteValues, int rotateValue) {
        byte[] result = new byte[byteValues.length];
        for (int i = 0; i < byteValues.length; i++) result[i] = rotateLeft(byteValues[i], rotateValue);
        return result;
    }

    public static int correctRotation(Integer rotateValue) {
        return Double.valueOf(Math.ceil(rotateValue.doubleValue() - (Math.floor(rotateValue.doubleValue() / 8.0d) * 8))).intValue();
    }
}
