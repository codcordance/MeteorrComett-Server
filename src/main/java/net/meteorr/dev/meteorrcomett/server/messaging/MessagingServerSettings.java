package net.meteorr.dev.meteorrcomett.server.messaging;

/**
 * @author RedLux
 */
public class MessagingServerSettings {
    private String hostname;
    private Integer port;


    public MessagingServerSettings(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
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
}
