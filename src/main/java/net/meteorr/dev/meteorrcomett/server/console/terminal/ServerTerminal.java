package net.meteorr.dev.meteorrcomett.server.console.terminal;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.utils.exception.*;
import net.meteorr.dev.meteorrcomett.server.utils.ClockTime;
import net.meteorr.dev.meteorrcomett.server.console.ColorCode;
import org.fusesource.jansi.AnsiConsole;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

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

    public synchronized void init() throws TerminalAlreadyInitializedException, TerminalInitializingException, TerminalFailedToInitializeException, InterruptedException {
        initAnsiConsole();
        initTerminal();
        if (!this.isInitialized()) throw new TerminalFailedToInitializeException();
        else {
            getTerminal().writer().println("");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|=-=--=--=--=--=--=--=--=--=--=--=--=--=-=--=-=--=--=--=--=--=--=--=--=--=--=-=|#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|                                                                              |#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|   ▄████████  ▄██████▄    ▄▄▄▄███▄▄▄▄      ▄████████     ███         ███      |#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|  ███    ███ ███    ███ ▄██▀▀▀███▀▀▀██▄   ███    ███ ▀█████████▄ ▀█████████▄  |#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|  ███    █▀  ███    ███ ███   ███   ███   ███    █▀     ▀███▀▀██    ▀███▀▀██  |#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|  ███        ███    ███ ███   ███   ███  ▄███▄▄▄         ███   ▀     ███   ▀  |#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|  ███    █▄  ███    ███ ███   ███   ███   ███    █▄      ███         ███      |#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|  ███    ███ ███    ███ ███   ███   ███   ███    ███     ███         ███      |#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|  ████████▀   ▀██████▀   ▀█   ███   █▀    ██████████    ▄████▀      ▄████▀    |#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|                                                                              |#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|               " + ColorCode.YELLOW.getAsciiCode() + "Meteorr Scalling Infrastructure & Messaging Tool" + ColorCode.RED.getAsciiCode() + "               |#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|                              " + ColorCode.CYAN.getAsciiCode() + "by RedLux & Niamor" + ColorCode.RED.getAsciiCode() + "                              |#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|                                                                              |#");
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|=-=--=--=--=--=--=--=--=--=--=--=--=--=-=--=-=--=--=--=--=--=--=--=--=--=--=-=|#");
            getTerminal().writer().println("");
            getInstance().print(MessageLevel.INFO,"Initializing console...");
            getInstance().print(MessageLevel.INFO,getTerminal().getName()+": " + getTerminal().getType());
            getInstance().print(MessageLevel.INFO,"Terminal initialiazed $GREENsuccessfully$RESET!");
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

    public void setTerminalReader(TerminalReader terminalReader) throws TerminalReaderAlreadySetException {
        if (getTerminalReader() != null) throw new TerminalReaderAlreadySetException();
        this.terminalReader = terminalReader;
        getInstance().print(MessageLevel.INFO, "$GREENTerminal reader successfully set!");
    }

    public void initReader() throws TerminalReaderNotSetException {
        if (getTerminalReader() == null) throw new TerminalReaderNotSetException();
        getTerminalReader().start();
        getInstance().print(MessageLevel.INFO, "$GREENTerminal reader successfully initialized!");
    }

    public void print(MessageLevel level, String... content) throws TerminalNotInitializedException, IOException, ThreadGroupNotInitializedException {
        if (!this.isInitialized()) throw new TerminalNotInitializedException();
        if (getInstance().getServerLogger() != null) getInstance().getServerLogger().write(level, content);
        try {
            TimeUnit.MILLISECONDS.sleep(100L);
        } catch (InterruptedException ignored) {}
        if (getTerminalReader() == null || !getTerminalReader().isRunning()) {
            StringBuilder builder = new StringBuilder();
            builder.append("$RESET");
            builder.append(ClockTime.getClockTime());
            builder.append(" [");
            builder.append(level.getFgColor().getAsciiCode());
            builder.append(level.getIdentifier());
            builder.append("$RESET");
            builder.append("] > ");
            builder.append(level.getBgColor().getAsciiCode());
            for (String line : content) builder.append(line).append("\n");
            builder.append("$RESET");
            String s = builder.toString();
            for (ColorCode c : ColorCode.values()) s = s.replace("$" + c.toString(), c.getAsciiCode());
            terminal.writer().print(s);
            terminal.writer().flush();
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("$RESET");
            builder.append(ClockTime.getClockTime());
            builder.append(" [");
            builder.append(level.getFgColor().getAsciiCode());
            builder.append(level.getIdentifier());
            builder.append("$RESET");
            builder.append("] > ");
            builder.append(level.getBgColor().getAsciiCode());
            int i = 1;
            for (String line : content) {
                builder.append(line).append( i != content.length ? "\n": "");
                i++;
            }
            builder.append("$RESET");
            String s = builder.toString();
            for (ColorCode c : ColorCode.values()) s = s.replace("$" + c.toString(), c.getAsciiCode());
            getTerminalReader().getReader().printAbove((s));
        }
    }

    public void stop() {
        getInstance().print(MessageLevel.INFO,"Stopping terminal...");
        this.initialized = false;
    }

    public void stopReader() throws TerminalNotRunningException, InterruptedException {
        getInstance().print(MessageLevel.INFO,"Stopping terminal reader...");
        getTerminalReader().end();
        getInstance().print(MessageLevel.INFO,"Terminal reader stopped $GREENsuccessfully$RESET!");
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
}
