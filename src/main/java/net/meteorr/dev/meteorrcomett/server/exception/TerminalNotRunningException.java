package net.meteorr.dev.meteorrcomett.server.exception;

import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le terminal n'est pas en cours
 */
public class TerminalNotRunningException extends MeteorrComettServerException {

    public TerminalNotRunningException() {
        super(MessageLevel.ERROR, "The Server Terminal isn't running / started (yet)!");
    }
}
