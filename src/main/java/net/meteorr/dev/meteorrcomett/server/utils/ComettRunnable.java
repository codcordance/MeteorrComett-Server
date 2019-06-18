package net.meteorr.dev.meteorrcomett.server.utils;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;

/**
 * @author RedLux
 *
 * Abstraction permettant de crée un runnable avec exceptions gérées
 */
public interface ComettRunnable {

    void lambda() throws Exception;

    default void run(MeteorrComettServer instance) {
        try {
            lambda();
        } catch (Exception e) {
            instance.getExceptionHandler().handle(e);
        }
    }
}
