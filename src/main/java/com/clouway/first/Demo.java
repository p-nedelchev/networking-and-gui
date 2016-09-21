package com.clouway.first;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class Demo {
    public static void main(String[] args) {
        DownloadAgent downloader = new DownloadAgent();
        try {
            URL testURL = new URL("https://pbs.twimg.com/profile_images/762369348300251136/5Obhonwa.jpg");
            Path saveLocation = Paths.get("test55");
            downloader.downloadFile(testURL, saveLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
