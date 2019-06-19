package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le console s'initialise
 */
public final class TerminalInitializingException extends MeteorrComettServerException {

    public TerminalInitializingException(Exception e) {
        super(MessageLevel.ERROR, "An error occured during the console initializing : " + e.getClass());
    }
}
