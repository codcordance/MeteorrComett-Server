package net.meteorr.dev.meteorrcomett.server.terminal.command;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.utils.WaitableInlineThread;
import net.meteorr.dev.meteorrcomett.server.utils.annotations.MeteorrComettWaitableThread;
import net.meteorr.dev.meteorrcomett.server.utils.exception.TerminalNotRunningException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ThreadGroupInitializedException;
import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.utils.ThreadsUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author RedSpri
 */
public class CommandManager {
    private final MeteorrComettServer instance;

    public CommandManager(MeteorrComettServer instance) {
        this.instance = instance;
    }

    public synchronized void proceed(String input) throws TerminalNotRunningException, InterruptedException, ThreadGroupInitializedException {
        if (input.equals("stop")) getInstance().stop();
        if (input.equals("testmsg")) {

            new WaitableInlineThread(getInstance().getThreadGroup(),"TestRun") {

                @Override
                public synchronized void run() {
                    for (int i = 0; i<10; i++) {
                        getInstance().print(MessageLevel.INFO, input, "test: $BG_CYAN" + (i + 1));
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            getInstance().getExceptionHandler().handle(e);
                        }
                    }
                }
            }.start();
        }
        if (input.equals("listthread")) {
            List<Thread> threads = ThreadsUtil.getGroupThreads(getInstance().getThreadGroup());
            final String[] s = {""};
            threads.forEach(thread -> s[0] += "--> " + thread.getName() + " (" + thread.getState() + "): " + thread.getClass().getName() + "\n");
            getInstance().print(MessageLevel.DEBUG, "Il y a actuellement " + threads.size() + " sous-threads: ", s[0]);
        }

    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }
}
