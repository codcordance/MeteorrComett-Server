package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

/**
 * @author RedLux
 *
 * Erreur quand le terminal n'est pas initialisé
 */
public final class TerminalNotInitializedException extends MeteorrComettServerException {

    public TerminalNotInitializedException() {
        super(MessageLevel.ERROR, "The Server Terminal isn't initialized!");
    }
}
