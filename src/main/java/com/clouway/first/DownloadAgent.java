package com.clouway.first;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class DownloadAgent {


    public void downloadFile(URL fileLocation, Path filePath) {
        URLConnection connection = null;
        try {
            connection = fileLocation.openConnection();
            InputStream in = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int read;
            if (! Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            while ((read = in.read(buffer)) != -1) {
                Files.write(filePath, Arrays.copyOf(buffer, read), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
