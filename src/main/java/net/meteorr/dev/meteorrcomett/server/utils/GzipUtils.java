package net.meteorr.dev.meteorrcomett.server.utils;

import net.meteorr.dev.meteorrcomett.server.utils.exception.GzipIOException;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author RedLux
 */
public class GzipUtils {

    public static void compressGZIP(File input, File output) throws GzipIOException {
        try (GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(output))){
            try (FileInputStream in = new FileInputStream(input)){
                byte[] buffer = new byte[1024];
                int len;
                while((len=in.read(buffer)) != -1){
                    out.write(buffer, 0, len);
                }
            }
        } catch (IOException e) {
            throw new GzipIOException(e);
        }
    }

    public static void decompressGzip(File input, File output) throws GzipIOException {
        try (GZIPInputStream in = new GZIPInputStream(new FileInputStream(input))){
            try (FileOutputStream out = new FileOutputStream(output)){
                byte[] buffer = new byte[1024];
                int len;
                while((len = in.read(buffer)) != -1){
                    out.write(buffer, 0, len);
                }
            }
        } catch (IOException e) {
            throw new GzipIOException(e);
        }
    }
}
