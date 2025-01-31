package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le lecteur de console est déjà défini
 */
public final class TerminalReaderAlreadySetException extends MeteorrComettServerException {

    public TerminalReaderAlreadySetException() {
        super(MessageLevel.ERROR, "The Server Terminal Reader is already set!");
    }
}
