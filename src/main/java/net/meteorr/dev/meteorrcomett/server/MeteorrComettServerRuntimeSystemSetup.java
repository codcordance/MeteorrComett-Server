package net.meteorr.dev.meteorrcomett.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author RedLux
 */
public class MeteorrComettServerRuntimeSystemSetup {

    MeteorrComettServerRuntimeSystemSetup() {
    }

    public void setup() {
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
    }
}
