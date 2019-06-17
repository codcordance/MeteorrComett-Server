package net.meteorr.dev.meteorrcomett.server.terminal;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.exception.TerminalAlreadyInitializedException;
import net.meteorr.dev.meteorrcomett.server.utils.ClockTime;
import net.meteorr.dev.meteorrcomett.server.utils.ColorCode;
import org.jline.terminal.Terminal;

/**
 * @author RedLux
 */
public class ServerTerminal {
    private final MeteorrComettServer instance;
    private Terminal terminal;
    private boolean initialized;

    public ServerTerminal(MeteorrComettServer instance) {
        this.instance = instance;
        this.initialized = true;
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void init() throws TerminalAlreadyInitializedException {
        initTerminal();
    }

    private void initTerminal() throws TerminalAlreadyInitializedException {
        if (this.isInitialized()) throw new TerminalAlreadyInitializedException();
    }

    public void print(MessageLevel level, String... content) {
        StringBuilder builder = new StringBuilder();
        builder.append(ColorCode.RESET.getAsciiCode());
        builder.append(ClockTime.getClockTime());
        builder.append(" [");
        builder.append(level.getFgColor().getAsciiCode());
        builder.append(level.getIdentifier());
        builder.append(ColorCode.RESET.getAsciiCode());
        builder.append("] > ");
        builder.append(level.getBgColor().getAsciiCode());
        for (String line : content) builder.append(line).append("\n");
        builder.append(ColorCode.RESET.getAsciiCode());
        System.out.print(builder.toString());
    }
}
