package net.meteorr.dev.meteorrcomett.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * @author RedLux
 *
 * Boostrap du serveur MeteorrComett
 */
public final class MeteorrComettServerBoostrap {

    public static void main(String[] args) throws Exception {
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int arg0) {
            }
        }));
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int arg0) {
            }
        }));
        System.setIn(new InputStream() {
            @Override
            public int read() {
                return 0;
            }
        });
        new MeteorrComettServer().start(Arrays.asList(args));
    }
}
