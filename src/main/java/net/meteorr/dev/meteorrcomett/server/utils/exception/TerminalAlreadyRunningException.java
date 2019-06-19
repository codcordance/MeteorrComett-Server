package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le console est déjà en cours
 */
public final class TerminalAlreadyRunningException extends MeteorrComettServerException {

    public TerminalAlreadyRunningException() {
        super(MessageLevel.ERROR, "The Server Terminal is already running and started!");
    }
}
