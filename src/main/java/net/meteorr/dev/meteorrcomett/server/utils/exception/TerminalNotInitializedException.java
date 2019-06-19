package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le console n'est pas initialisé
 */
public final class TerminalNotInitializedException extends MeteorrComettServerException {

    public TerminalNotInitializedException() {
        super(MessageLevel.ERROR, "The Server Terminal isn't initialized!");
    }
}
