package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand la commande est déjà enregistrée
 */
public final class CommandAlreadyExistException extends MeteorrComettServerException {

    public CommandAlreadyExistException(String label) {
        super(MessageLevel.ERROR, "A command with the name/label " + label + "already exist!");
    }
}
