package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le groupe de thread n'est pas initialisé
 */
public final class ThreadGroupNotInitializedException extends MeteorrComettServerException {

    public ThreadGroupNotInitializedException() {
        super(MessageLevel.ERROR, "The Server Thread Group isn't initialized!");
    }
}
