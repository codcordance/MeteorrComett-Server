package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le groupe de thread n'est pas initialisé
 */
public final class ThreadGroupInitializedException extends MeteorrComettServerException {

    public ThreadGroupInitializedException() {
        super(MessageLevel.ERROR, "The Server Thread Group isn't initialized!");
    }
}
