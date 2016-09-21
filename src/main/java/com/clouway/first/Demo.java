package com.clouway.first;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class Demo {
    public static void main(String[] args) {
        DownloadAgent downloader = new DownloadAgent();
        try {
            URL testURL = new URL("http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2010/01/google_logo5.jpg");
            OutputStream out = new FileOutputStream("test");
            downloader.downloadFile(testURL, out);
        } catch (MalformedURLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
