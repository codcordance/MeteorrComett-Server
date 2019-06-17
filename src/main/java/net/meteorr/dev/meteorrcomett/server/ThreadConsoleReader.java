package net.meteorr.dev.meteorrcomett.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author RedLux
 */
public class ThreadConsoleReader extends Thread {

    MeteorrComettServer server;

    public ThreadConsoleReader(MeteorrComettServer server) {
        super("MeteorrComett Console reader");
        this.server = server;
    }

    public void run() {
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));

        String command;

        try {
            while (this.server.isRunning() && (command = bufferedreader.readLine()) != null) {
                this.server.issueCommand(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**try {
            while (!this.server.isStopped() && this.server.isRunning() && (s = bufferedreader.readLine()) != null) {
                this.server.issueCommand(s, this.server);
            }
        } catch (IOException ioexception) {
            DedicatedServer.getLogger().error("Exception handling console input", ioexception);
        }**/
    }
}
