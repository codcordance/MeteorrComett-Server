package net.meteorr.dev.meteorrcomett.server.messaging.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author RedLux
 */
public class MessagingServerConfigJSON {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static String getJsonFromConfig(MessagingServerConfig config) {
        return gson.toJson(config);
    }

    public static MessagingServerConfig getConfigFromJson(String json) {
        return gson.fromJson(json, MessagingServerConfig.class);
    }
}
