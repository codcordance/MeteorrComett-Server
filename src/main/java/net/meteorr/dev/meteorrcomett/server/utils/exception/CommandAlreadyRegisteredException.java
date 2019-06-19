package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand une commande avec un nom déjà existant tente d'être enregistré
 */
public final class CommandAlreadyRegisteredException extends MeteorrComettServerException {

    public CommandAlreadyRegisteredException() {
        super(MessageLevel.ERROR, "The command is already registered!");
    }
}
