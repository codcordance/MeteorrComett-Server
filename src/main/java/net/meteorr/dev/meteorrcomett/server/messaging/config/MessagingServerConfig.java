package net.meteorr.dev.meteorrcomett.server.messaging.config;

/**
 * @author RedLux
 */
public class MessagingServerConfig {
    private String hostname;
    private Integer port;
    private boolean ssl;
    private String code;
    private String secondcode;
    private String certfile;
    private String keyfile;
    private String passfile;

    MessagingServerConfig(String hostname, Integer port, boolean ssl, String code, String secondcode, String certfile, String keyfile, String passfile) {
        this.hostname = hostname;
        this.port = port;
        this.ssl = ssl;
        this.code = code;
        this.secondcode = secondcode;
        this.certfile = certfile;
        this.keyfile = keyfile;
        this.passfile = passfile;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecondcode() {
        return secondcode;
    }

    public void setSecondcode(String secondcode) {
        this.secondcode = secondcode;
    }

    public String getCertfile() {
        return certfile;
    }

    public void setCertfile(String certfile) {
        this.certfile = certfile;
    }

    public String getKeyfile() {
        return keyfile;
    }

    public void setKeyfile(String keyfile) {
        this.keyfile = keyfile;
    }

    public String getPassfile() {
        return passfile;
    }

    public void setPassfile(String passfile) {
        this.passfile = passfile;
    }
}
