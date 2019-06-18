package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le lecteur de terminal est déjà défini
 */
public final class TerminalReaderAlreadySetException extends MeteorrComettServerException {

    public TerminalReaderAlreadySetException() {
        super(MessageLevel.ERROR, "The Server Terminal Reader is already set!");
    }
}
