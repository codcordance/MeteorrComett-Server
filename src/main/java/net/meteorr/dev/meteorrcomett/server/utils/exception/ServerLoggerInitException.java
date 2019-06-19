package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur lors de l'initialisation du Server Logger
 */
public final class ServerLoggerInitException extends MeteorrComettServerException {

    public ServerLoggerInitException(Exception e) {
        super(MessageLevel.ERROR, "The Server Logger failed to init: " + e.getClass());
    }
}
