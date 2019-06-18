package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le terminal est déjà en cours
 */
public final class TerminalAlreadyRunningException extends MeteorrComettServerException {

    public TerminalAlreadyRunningException() {
        super(MessageLevel.ERROR, "The Server Terminal is already running and started!");
    }
}
