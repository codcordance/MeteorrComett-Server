package net.meteorr.dev.meteorrcomett.server.exception;

import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le terminal est déjà en cours
 */
public class TerminalAlreadyRunningException extends MeteorrComettServerException {

    public TerminalAlreadyRunningException() {
        super(MessageLevel.ERROR, "The Server Terminal is already running and started!");
    }
}
