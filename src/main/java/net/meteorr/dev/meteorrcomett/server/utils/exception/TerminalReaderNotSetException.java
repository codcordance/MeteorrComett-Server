package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le lecteur de console n'est pas défini
 */
public final class TerminalReaderNotSetException extends MeteorrComettServerException {

    public TerminalReaderNotSetException() {
        super(MessageLevel.ERROR, "The Server Terminal Reader isn't set!");
    }
}
