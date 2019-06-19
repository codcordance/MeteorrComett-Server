package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le console n'a pas réussi à s'initialiser
 */
public final class TerminalFailedToInitializeException extends MeteorrComettServerException {

    public TerminalFailedToInitializeException() {
        super(MessageLevel.CRITICAL, "The Server Terminal failed to initialize!");
    }
}
