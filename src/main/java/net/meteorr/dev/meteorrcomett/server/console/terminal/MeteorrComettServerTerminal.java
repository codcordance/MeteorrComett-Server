package net.meteorr.dev.meteorrcomett.server.console.terminal;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.console.ColorCode;
import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.utils.ClockTime;
import net.meteorr.dev.meteorrcomett.server.utils.exception.*;
import org.fusesource.jansi.AnsiConsole;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author RedLux
 */
public class MeteorrComettServerTerminal {
    private final MeteorrComettServer instance;
    private Terminal terminal;
    private boolean initialized;
    private MeteorrComettServerTerminalReader meteorrComettServerTerminalReader;

    public MeteorrComettServerTerminal(MeteorrComettServer instance) {
        this.instance = instance;
        this.initialized = false;
        this.meteorrComettServerTerminalReader = null;
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
            getTerminal().writer().println(ColorCode.RED.getAsciiCode() + "#|                           " + ColorCode.CYAN.getAsciiCode() + "by RedLux + Meteorr Devs" + ColorCode.RED.getAsciiCode() + "                           |#");
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

    public void initReader() throws TerminalReaderNotSetException {
        if (getMeteorrComettServerTerminalReader() == null) throw new TerminalReaderNotSetException();
        getMeteorrComettServerTerminalReader().start();
        getInstance().print(MessageLevel.INFO, "$GREENTerminal reader successfully initialized!");
    }

    public void print(MessageLevel level, String... content) throws TerminalNotInitializedException, IOException, ThreadGroupNotInitializedException {
        if (!this.isInitialized()) throw new TerminalNotInitializedException();
        if (getInstance().getMeteorrComettServerLogger() != null)
            getInstance().getMeteorrComettServerLogger().write(level, content);
        try {
            TimeUnit.MILLISECONDS.sleep(100L);
        } catch (InterruptedException ignored) {}
        if (getMeteorrComettServerTerminalReader() == null || !getMeteorrComettServerTerminalReader().isRunning()) {
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
            getMeteorrComettServerTerminalReader().getReader().printAbove((s));
        }
    }

    public void stopReader() throws TerminalNotRunningException, InterruptedException {
        getInstance().print(MessageLevel.INFO,"Stopping terminal reader...");
        getMeteorrComettServerTerminalReader().end();
        getInstance().print(MessageLevel.INFO,"Terminal reader stopped $GREENsuccessfully$RESET!");
    }

    public void stop() {
        getInstance().print(MessageLevel.INFO, "Stopping terminal...");
        this.initialized = false;
    }

    public MeteorrComettServerTerminalReader getMeteorrComettServerTerminalReader() {
        return this.meteorrComettServerTerminalReader;
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public void setMeteorrComettServerTerminalReader(MeteorrComettServerTerminalReader meteorrComettServerTerminalReader) throws TerminalReaderAlreadySetException {
        if (getMeteorrComettServerTerminalReader() != null) throw new TerminalReaderAlreadySetException();
        this.meteorrComettServerTerminalReader = meteorrComettServerTerminalReader;
        getInstance().print(MessageLevel.INFO, "$GREENTerminal reader successfully set!");
    }

    public Terminal getTerminal() {
        return this.terminal;
    }

    public boolean isInitialized() {
        return this.initialized;
    }
}
