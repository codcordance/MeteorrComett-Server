package net.meteorr.dev.meteorrcomett.server;

import java.util.Arrays;

/**
 * @author RedLux
 *
 * Boostrap du serveur MeteorrComett
 */
public final class MeteorrComettServerBoostrap {

    public static void main(String[] args) throws Exception {
        new MeteorrComettServer().start(Arrays.asList(args));
    }
}
