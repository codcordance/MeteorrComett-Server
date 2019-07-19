package net.meteorr.dev.meteorrcomett.server.messaging.security;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;

public class MessagingServerSecurityContextBuilder {
    public SslContext build(String certificate, String key) throws SSLException {
        return SslContextBuilder.forServer(getInputStreamFromString(certificate), getInputStreamFromString(key)).build();
    }

    public SslContext build(String certificate, String key, String pass) throws SSLException {
        return SslContextBuilder.forServer(getInputStreamFromString(certificate), getInputStreamFromString(key), pass).build();
    }

    public SslContext build() throws SSLException {
        try {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            return SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } catch (CertificateException e) {
            return null;
        }
    }

    private InputStream getInputStreamFromString(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }
}
