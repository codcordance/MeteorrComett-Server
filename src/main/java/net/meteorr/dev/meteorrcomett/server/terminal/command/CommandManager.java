package net.meteorr.dev.meteorrcomett.server.terminal.command;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.exception.TerminalNotRunningException;
import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;

/**
 * @author RedSpri
 */
public class CommandManager {
    private final MeteorrComettServer instance;

    public CommandManager(MeteorrComettServer instance) {
        this.instance = instance;
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public void proceed(String input) throws TerminalNotRunningException {
        if (input.equals("stop")) getInstance().stop();
        getInstance().print(MessageLevel.DEBUG, "command proceed: " + input);
    }
}
