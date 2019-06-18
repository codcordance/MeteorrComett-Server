package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le terminal s'initialise
 */
public final class TerminalInitializingException extends MeteorrComettServerException {

    public TerminalInitializingException(Exception e) {
        super(MessageLevel.ERROR, "An error occured during the terminal initializing : " + e.getClass());
    }
}
