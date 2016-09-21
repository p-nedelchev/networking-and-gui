package com.clouway.first;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;


/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class DownloadAgentTests {
    private DownloadAgent downloader;

    @Before
    public void setUp() throws Exception {
        downloader = new DownloadAgent();
    }

    @Test
    public void happyPath() throws Exception {
        URL filePath = new URL("file:///home/clouway/Pictures/test.png");
        Path saveLocation = Paths.get("test.png");
        downloader.downloadFile(filePath, saveLocation);
        long downloadedLength = saveLocation.toFile().length();
        System.out.println(downloadedLength);
        long expectedLength = 22183;
        assertTrue(expectedLength == downloadedLength);
    }
}
