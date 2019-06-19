package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le console est déjà initialisé
 */
public final class TerminalAlreadyInitializedException extends MeteorrComettServerException {
    public TerminalAlreadyInitializedException() {
        super(MessageLevel.WARNING, "The Server Terminal is already initialized!");
    }
}
