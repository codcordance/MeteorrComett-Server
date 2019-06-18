package net.meteorr.dev.meteorrcomett.server.terminal;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.exception.*;
import net.meteorr.dev.meteorrcomett.server.utils.ClockTime;
import net.meteorr.dev.meteorrcomett.server.utils.ColorCode;
import org.fusesource.jansi.AnsiConsole;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author RedLux
 */
public class ServerTerminal {
    private final MeteorrComettServer instance;
    private Terminal terminal;
    private boolean initialized;
    private TerminalReader terminalReader;

    public ServerTerminal(MeteorrComettServer instance) {
        this.instance = instance;
        this.initialized = false;
        this.terminalReader = null;
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public TerminalReader getTerminalReader() {
        return this.terminalReader;
    }

    public Terminal getTerminal() {
        return this.terminal;
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
            terminal.writer().println("");
            getInstance().print(MessageLevel.INFO,"Initializing terminal...");
            getInstance().print(MessageLevel.INFO,terminal.getName()+": "+terminal.getType());
            getInstance().print(MessageLevel.INFO,"Terminal initalized " + ColorCode.GREEN.getAsciiCode() + "successfully" + ColorCode.RESET.getAsciiCode() + "!");
            getInstance().print(MessageLevel.INFO,"Starting terminal...");
            initReader();
            getInstance().print(MessageLevel.INFO,"Terminal started " + ColorCode.GREEN.getAsciiCode() + "successfully" + ColorCode.RESET.getAsciiCode() + "!");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getInstance().print(MessageLevel.DEBUG, "hello!!");

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

    private void initReader() {
        this.terminalReader = new TerminalReader(getInstance(), getTerminal());
        getTerminalReader().start();
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

    public void stop() throws TerminalNotRunningException {
        getInstance().print(MessageLevel.INFO,"Stopping terminal...");
        getTerminalReader().end();
        getInstance().print(MessageLevel.INFO,"Terminal stopped " + ColorCode.GREEN.getAsciiCode() + "successfully" + ColorCode.RESET.getAsciiCode() + "!");

    }
}
