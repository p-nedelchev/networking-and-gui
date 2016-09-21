package com.clouway.first;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class DownloadAgent {


    public void downloadFile(URL fileLocation, OutputStream out) {
        URLConnection connection = null;
        try {
            connection = fileLocation.openConnection();
            InputStream in = connection.getInputStream();
            byte [] buffer = new byte[1024];
            int readBytes;
            while ((readBytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, readBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
