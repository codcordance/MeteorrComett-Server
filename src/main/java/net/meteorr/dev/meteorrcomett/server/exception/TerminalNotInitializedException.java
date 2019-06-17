package net.meteorr.dev.meteorrcomett.server.exception;

import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le terminal n'est pas initialisé
 */
public class TerminalNotInitializedException extends MeteorrComettServerException {

    public TerminalNotInitializedException() {
        super(MessageLevel.ERROR, "The Server Terminal isn't initialized!");
    }
}
