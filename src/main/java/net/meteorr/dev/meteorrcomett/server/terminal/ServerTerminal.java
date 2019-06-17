package net.meteorr.dev.meteorrcomett.server.terminal;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.exception.TerminalAlreadyInitializedException;
import net.meteorr.dev.meteorrcomett.server.exception.TerminalFailedToInitializeException;
import net.meteorr.dev.meteorrcomett.server.exception.TerminalInitializingException;
import net.meteorr.dev.meteorrcomett.server.exception.TerminalNotInitializedException;
import net.meteorr.dev.meteorrcomett.server.utils.ClockTime;
import net.meteorr.dev.meteorrcomett.server.utils.ColorCode;
import org.fusesource.jansi.AnsiConsole;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

import java.io.IOException;

/**
 * @author RedLux
 */
public class ServerTerminal {
    private final MeteorrComettServer instance;
    private Terminal terminal;
    private boolean initialized;

    public ServerTerminal(MeteorrComettServer instance) {
        this.instance = instance;
        this.initialized = false;
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void init() throws TerminalAlreadyInitializedException, TerminalInitializingException, TerminalFailedToInitializeException {
        initAnsiConsole();
        initTerminal();
        if (!this.isInitialized()) throw new TerminalFailedToInitializeException();
        else {
            System.out.println(ansi().eraseScreen());
            terminal.writer().println("");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|==========================================================|#");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|                                                          |#");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|   ██████╗ ██████╗ ███╗   ███╗███████╗████████╗████████╗  |#");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|  ██╔════╝██╔═══██╗████╗ ████║██╔════╝╚══██╔══╝╚══██╔══╝  |#");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|  ██║     ██║   ██║██║╚██╔╝██║██╔══╝     ██║      ██║     |#");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|  ██║     ██║   ██║██║╚██╔╝██║██╔══╝     ██║      ██║     |#");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|  ╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗   ██║      ██║     |#");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|   ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝   ╚═╝      ╚═╝     |#");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|                                                          |#");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|     " + ColorCode.YELLOW.getAsciiCode() + "Meteorr Scalling Infrastructure & Messaging Tool" + ColorCode.RED.getAsciiCode()+"     |#");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|                    " + ColorCode.CYAN.getAsciiCode() + "by RedLux & Niamor" + ColorCode.RED.getAsciiCode() + "                    |#");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|                                                          |#");
            terminal.writer().println(ColorCode.RED.getAsciiCode() + "#|==========================================================|#");
            getInstance().print(MessageLevel.INFO,terminal.getName()+": "+terminal.getType());
        }
    }

    private void initAnsiConsole() throws TerminalInitializingException {
        try {
            AnsiConsole.systemInstall();
        } catch (Exception e) {
            throw new TerminalInitializingException(e);
        }

    }

    private void initTerminal() throws TerminalAlreadyInitializedException, TerminalInitializingException {
        if (this.isInitialized()) throw new TerminalAlreadyInitializedException();
        TerminalBuilder builder = TerminalBuilder.builder();
        try {
            terminal = builder.build();
        } catch (Exception e) {
            throw new TerminalInitializingException(e);
        }
        this.initialized = true;

    }

    public void print(MessageLevel level, String... content) throws TerminalNotInitializedException {
        if (!this.isInitialized()) throw new TerminalNotInitializedException();
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
        terminal.writer().print(builder.toString());
        terminal.writer().flush();
    }
}
