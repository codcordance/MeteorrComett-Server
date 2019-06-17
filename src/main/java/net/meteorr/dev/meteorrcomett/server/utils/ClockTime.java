package net.meteorr.dev.meteorrcomett.server.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author RedLux
 */
public class ClockTime {
    private static final DateTimeFormatter clockFormater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final LocalDateTime clockTime = LocalDateTime.now();

    public static String getClockTime() {
        return clockFormater.format(clockTime);
    }
}
