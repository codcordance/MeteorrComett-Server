package net.meteorr.dev.meteorrcomett.server.console.command;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;

/**
 * @author RedLux
 */
public abstract class MeteorrComettServerCommand {
    private final String label;

    public MeteorrComettServerCommand(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public abstract void execute(MeteorrComettServer instance, String[] args) throws Exception;
}
