package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand un composant du seveur n'a pas réussi à s'initialiser
 */
public final class ComponentFailedToInitializeException extends MeteorrComettServerException {

    public ComponentFailedToInitializeException(Exception e) {
        super(MessageLevel.CRITICAL, "A component of the server failed to initialize: " + e.getClass());
    }
}
