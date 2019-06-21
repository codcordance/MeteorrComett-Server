package net.meteorr.dev.meteorrcomett.server.console.terminal;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.utils.exception.TerminalAlreadyRunningException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.TerminalNotRunningException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ThreadGroupNotInitializedException;
import net.meteorr.dev.meteorrcomett.server.utils.annotations.MeteorrComettImportantThread;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;

import java.util.concurrent.TimeUnit;

/**
 * @author RedLux
 */
@MeteorrComettImportantThread(name="MeteorrComettServerTerminalReader")
public class TerminalReader extends Thread {
    private final MeteorrComettServer instance;
    private Terminal terminal;
    private boolean running;
    private LineReader reader;

    public TerminalReader(MeteorrComettServer instance, Terminal terminal) throws ThreadGroupNotInitializedException {
        super(instance.getThreadGroup(),"MeteorrComettServerTerminalReader");
        this.instance = instance;
        this.running = false;
        this.terminal = terminal;
        this.reader = null;
    }

    public void run() {
        super.run();
        getInstance().print(MessageLevel.INFO, "Running terminal started.");
        if (this.isRunning()) try {
            throw new TerminalAlreadyRunningException();
        } catch (TerminalAlreadyRunningException e) {
            getInstance().getExceptionHandler().handle(e);
        }
        this.running = true;
        this.reader = LineReaderBuilder.builder().variable(LineReader.SECONDARY_PROMPT_PATTERN, "%M%P > ").terminal(getTerminal()).build();
        while(isRunning()) {
            try {
                if (!getInstance().isChecked()) {
                    String line;
                    do {
                        line = getReader().readLine("[yes/no]: ");
                    } while (!line.equalsIgnoreCase("no") && !line.equalsIgnoreCase("yes"));
                    getInstance().checkconsume(line.equalsIgnoreCase("yes"));
                }
                getInstance().getCommandManager().proceed(getReader().readLine("%M%P > "));
            } catch (Exception e) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                    if (isRunning()) getInstance().getExceptionHandler().handle(e);
                } catch (InterruptedException ignored) {}
            }

        }
    }

    public void end() throws TerminalNotRunningException, InterruptedException {
        if(!this.isRunning()) throw new TerminalNotRunningException();
        this.running = false;
        TimeUnit.SECONDS.sleep(1);
        this.interrupt();
    }

    public Terminal getTerminal() {
        return this.terminal;
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public Boolean isRunning() {
        return this.running;
    }

    public LineReader getReader() {
        return this.reader;
    }
}
