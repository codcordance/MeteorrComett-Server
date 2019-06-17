package net.meteorr.dev.meteorrcomett.server.exception;

import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

public class MeteorrComettServerException extends Exception {
    private MessageLevel level;

    public MeteorrComettServerException(MessageLevel level, String message) {
        super(message);
        this.level = level;
    }

    public MessageLevel getLevel() {
        return level;
    }
}
