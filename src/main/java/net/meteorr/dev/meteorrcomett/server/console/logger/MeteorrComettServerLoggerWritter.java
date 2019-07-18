package net.meteorr.dev.meteorrcomett.server.console.logger;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.console.ColorCode;
import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.utils.ClockTime;
import net.meteorr.dev.meteorrcomett.server.utils.annotations.MeteorrComettWaitableThread;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ServerLoggerWriteException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ThreadGroupNotInitializedException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author RedLux
 */
@MeteorrComettWaitableThread(timeout = 1000)
public class MeteorrComettServerLoggerWritter extends Thread {
    private final MeteorrComettServer instance;
    private FileWriter fileWriter;
    private MessageLevel messageLevel;
    private String[] content;

    public MeteorrComettServerLoggerWritter(MeteorrComettServer instance, File fileWriter) throws IOException, ThreadGroupNotInitializedException {
        super(instance.getThreadGroup(),"MeteorrComettServerLoggerWritter");
        this.instance = instance;
        this.fileWriter = new FileWriter(fileWriter, true);
    }

    public MeteorrComettServerLoggerWritter log(MessageLevel level, String... content) {
        this.messageLevel = level;
        this.content = content;
        return this;
    }

    public void run() {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(ClockTime.getClockTime());
            builder.append(" [");
            builder.append(getMessageLevel().getIdentifier());
            builder.append("] > ");
            for (String line : getContent()) builder.append(line).append("\n");
            String s = builder.toString();
            for (ColorCode c : ColorCode.values()) s = s.replace("$" + c.toString(), "");
            for (ColorCode c : ColorCode.values()) s = s.replace(c.getAsciiCode(), "");
            getFileWriter().write(s);
            getFileWriter().close();
        } catch (IOException e) {
            try {
                throw new ServerLoggerWriteException(e);
            } catch (ServerLoggerWriteException ex) {
                getInstance().getExceptionHandler().handle(ex);
            }
        }
    }

    public MeteorrComettServer getInstance() {
        return instance;
    }

    public FileWriter getFileWriter() {
        return fileWriter;
    }

    public MessageLevel getMessageLevel() {
        return messageLevel;
    }

    public String[] getContent() {
        return content;
    }
}
