package net.meteorr.dev.meteorrcomett.server.console;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ServerLoggerInitException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ThreadGroupNotInitializedException;

import java.io.File;
import java.io.IOException;

public class ServerLogger {
    private final MeteorrComettServer instance;
    private final File logFile;

    public ServerLogger(MeteorrComettServer instance) throws ServerLoggerInitException {
        File logFile;
        this.instance = instance;
        try {
            logFile = init();
        } catch (Exception e) {
            throw new ServerLoggerInitException(e);
        }
        this.logFile = logFile;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File init() throws IOException {
        File dir = new File("logs/");
        if (!dir.exists()) dir.mkdir();
        File log = new File("logs/latest.log");
        if (!log.exists()) log.createNewFile();
        else {
            log.delete();
            log.createNewFile();
        }
        return log;
    }

    public void write(MessageLevel level, String... content) throws IOException, ThreadGroupNotInitializedException {
        new LoggerWritter(getInstance(), getLogFile()).log(level, content).start();
    }

    public MeteorrComettServer getInstance() {
        return instance;
    }

    public File getLogFile() {
        return logFile;
    }

}
