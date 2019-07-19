package net.meteorr.dev.meteorrcomett.server.messaging.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MessagingServerSecurityFileReader {
    public String readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) content.append(line);
        reader.close();
        return content.toString();
    }
}
