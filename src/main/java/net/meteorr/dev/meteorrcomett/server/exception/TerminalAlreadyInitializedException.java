package net.meteorr.dev.meteorrcomett.server.exception;

import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le terminal est déjà initialisé
 */
public class TerminalAlreadyInitializedException extends MeteorrComettServerException {
    public TerminalAlreadyInitializedException() {
        super(MessageLevel.WARNING, "The Server Terminal is already initialized!");
    }
}
