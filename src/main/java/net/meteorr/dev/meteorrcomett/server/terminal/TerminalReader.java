package net.meteorr.dev.meteorrcomett.server.terminal;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.exception.TerminalAlreadyRunningException;
import net.meteorr.dev.meteorrcomett.server.exception.TerminalNotRunningException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;

/**
 * @author RedSpri
 */
public class TerminalReader extends Thread {
    private final MeteorrComettServer instance;
    private Terminal terminal;
    private boolean running;
    private LineReader reader;

    public TerminalReader(MeteorrComettServer instance, Terminal terminal1) {
        this.instance = instance;
        this.running = false;
        this.terminal = terminal;
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public boolean isRunning() {
        return this.running;
    }

    public LineReader getReader() {
        return this.reader;
    }

    public void run() {
        super.run();
        if (this.isRunning()) try {
            throw new TerminalAlreadyRunningException();
        } catch (TerminalAlreadyRunningException e) {
            getInstance().getExceptionHandler().handle(e);
        }
        this.running = true;
        this.reader = LineReaderBuilder.builder().variable(LineReader.SECONDARY_PROMPT_PATTERN, "%M%P > ").terminal(this.terminal).build();
        while(this.isRunning()) {
            try {
                getInstance().getCommandManager().proceed(getReader().readLine());
            } catch (Exception e) {
                getInstance().getExceptionHandler().handle(e);
            }
        }
    }

    public void end() throws TerminalNotRunningException {
        if(!this.isRunning()) throw new TerminalNotRunningException();
        this.running = false;
    }
}
