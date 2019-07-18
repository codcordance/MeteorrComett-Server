package net.meteorr.dev.meteorrcomett.server.messaging.config;

import net.meteorr.dev.meteorrcomett.server.messaging.MessagingServerBootstrap;
import net.meteorr.dev.meteorrcomett.server.messaging.logging.MessagingServerLoggerHandler;

import java.io.*;

/**
 * @author RedLux
 */
public class MessagingServerConfigLoader {
    private MessagingServerBootstrap messagingServerBootstrap;

    public MessagingServerConfigLoader(MessagingServerBootstrap messagingServerBootstrap) {
        this.messagingServerBootstrap = messagingServerBootstrap;
    }

    public MessagingServerConfig load() throws IOException {
        MessagingServerConfig config = new MessagingServerDefaultConfig();
        getLogger().logInfo("Loading config...");
        getLogger().logInfo("Getting config file...");
        File configFile = new File("messaging/config.json");
        if (!configFile.exists()) {
            getLogger().logInfo("Config file doesn't exists, creating it...");
            configFile.createNewFile();
            getLogger().logInfo("$GREENCreated!");
            getLogger().logInfo("Writing default config into the config file...");
            PrintWriter writer = new PrintWriter(new FileWriter(configFile));
            writer.write(MessagingServerConfigJSON.getJsonFromConfig(config));
            writer.flush();
            writer.close();
            getLogger().logInfo("$GREENWritten!");
        } else {
            getLogger().logInfo("$GREENConfig file exists!");
            getLogger().logInfo("Reading the config file...");
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) content.append(line);
            reader.close();
            getLogger().logInfo("$GREENRead!");
            getLogger().logInfo("Getting config...");
            try {
                config = MessagingServerConfigJSON.getConfigFromJson(content.toString());
            } catch (Exception e) {
                throw new InvalidObjectException("The config is invalid!");
            }
            getLogger().logInfo("$GREENGet!");
        }
        if (!checkconfig(config)) throw new InvalidObjectException("The config is invalid!");
        getLogger().logInfo("Config loaded $GREENsuccessfully$RESET!");
        return config;
    }

    private boolean checkconfig(MessagingServerConfig config) {
        return config != null && config.getHostname() != null && config.getPort() != null;
    }

    public MessagingServerLoggerHandler getLogger() {
        return messagingServerBootstrap.getMessagingServerLoggerHandler();
    }
}
