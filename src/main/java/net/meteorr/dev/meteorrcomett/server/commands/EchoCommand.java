package net.meteorr.dev.meteorrcomett.server.commands;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.console.command.MeteorrComettServerCommand;

import java.util.Arrays;

/**
 * @author RedLux
 */
public class EchoCommand extends MeteorrComettServerCommand {
    public EchoCommand() {
        super("echo");
    }

    @Override
    public void execute(MeteorrComettServer instance, String[] args) {
        final String[] s = {""};
        Arrays.asList(args).forEach(arg -> s[0] += arg + " ");
        instance.print(MessageLevel.INFO, s[0]);
    }
}
