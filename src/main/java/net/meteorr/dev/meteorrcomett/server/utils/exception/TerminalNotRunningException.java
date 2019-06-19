package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le console n'est pas en cours
 */
public final class TerminalNotRunningException extends MeteorrComettServerException {

    public TerminalNotRunningException() {
        super(MessageLevel.ERROR, "The Server Terminal isn't running / started (yet)!");
    }
}
