package com.clouway.first;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class DownloadAgentTests {
    private DownloadAgent downloader;
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


    @Before
    public void setUp() throws Exception {
        downloader = new DownloadAgent();
    }

    @After
    public void tearDown() throws Exception {
        folder.delete();
    }

    @Test
    public void happyPath() throws Exception {
        URL filePath = ClassLoader.getSystemResource("test.png");
        File downloadedFile = folder.newFile("test.png");
        OutputStream outputStream = new FileOutputStream(downloadedFile);
        downloader.downloadFile(filePath, outputStream);
        long originalSize = Files.size(Paths.get(filePath.toURI()));
        long downloadedSize = Files.size(Paths.get(downloadedFile.toURI()));
        assertThat(originalSize, is(downloadedSize));
    }
}
