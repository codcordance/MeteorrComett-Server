package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur lors de l'écriture par le Server Logger
 */
public final class ServerLoggerWriteException extends MeteorrComettServerException {

    public ServerLoggerWriteException(Exception e) {
        super(MessageLevel.WARNING, "The Server Logger failed to write: " + e.getClass());
    }
}
