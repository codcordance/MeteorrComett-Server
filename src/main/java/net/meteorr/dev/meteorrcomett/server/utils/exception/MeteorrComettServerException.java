package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedSpri
 *
 * Classe servant de modèle pour les exceptions personnalisées de MeteorrComett
 */
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
