package net.meteorr.dev.meteorrcomett.server.utils.exception;

import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;

import java.io.IOException;

/**
 * @author RedLux
 *
 * Erreur IO lors d'une opération lié aux fichiers GZIP
 */
public final class GzipIOException extends MeteorrComettServerException {

    public GzipIOException(IOException io) {
        super(MessageLevel.ERROR, "An IO exception occured during the gzip operatrion:" + io.getClass());
    }
}
