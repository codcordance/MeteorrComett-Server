package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand la commande n'a pas pu être chargé avec reflection
 */
public final class FailedToReflectCommandException extends MeteorrComettServerException {

    public FailedToReflectCommandException(Exception e) {
        super(MessageLevel.ERROR, "Error occured during the command reflection loading :" + e.getClass());
    }
}
