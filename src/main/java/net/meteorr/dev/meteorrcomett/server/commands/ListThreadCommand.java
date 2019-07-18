package net.meteorr.dev.meteorrcomett.server.commands;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.console.command.MeteorrComettServerCommand;
import net.meteorr.dev.meteorrcomett.server.utils.ThreadsUtils;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ThreadGroupNotInitializedException;

import java.util.List;

/**
 * @author RedLux
 */
public class ListThreadCommand extends MeteorrComettServerCommand {
    public ListThreadCommand() {
        super("listthread");
    }

    @Override
    public void execute(MeteorrComettServer instance, String[] args) throws ThreadGroupNotInitializedException {
        List<Thread> threads = ThreadsUtils.getGroupThreads(instance.getThreadGroup());
        final String[] s = {""};
        threads.forEach(thread -> s[0] += "--> " + thread.getName() + " (" + thread.getState() + "): " + thread.getClass().getName() + "\n");
        instance.print(MessageLevel.DEBUG, "Il y a actuellement " + threads.size() + " sous-threads: ", s[0]);
    }
}
