package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le terminal n'a pas réussi à s'initialiser
 */
public final class TerminalFailedToInitializeException extends MeteorrComettServerException {

    public TerminalFailedToInitializeException() {
        super(MessageLevel.CRITICAL, "The Server Terminal failed to initialize!");
    }
}
