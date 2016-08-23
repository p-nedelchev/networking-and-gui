package com.clouway.downloadagent;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class DownloadAgentTest {
  JUnitRuleMockery context = new JUnitRuleMockery();
  ProgressBar progressBar = context.mock(ProgressBar.class);

  @Test
  public void happyPath () throws Exception {
    DownloadAgent downloadAgent = new DownloadAgent(progressBar);
    context.checking(new Expectations() {{
      allowing(progressBar).update(0);
      allowing(progressBar).update(10);
      allowing(progressBar).update(20);
      allowing(progressBar).update(30);
      allowing(progressBar).update(40);
      allowing(progressBar).update(50);
      allowing(progressBar).update(60);
      allowing(progressBar).update(70);
      allowing(progressBar).update(80);
      allowing(progressBar).update(90);
      allowing(progressBar).update(100);

    }});
    File actual=downloadAgent.downloadFile("File:/home/clouway/Downloads/online-url-bibanews.jpg", "somefilefortest.jpg");
    File expected = new File("otherfile.jpg");
    assertThat(actual.getTotalSpace(), is(expected.getTotalSpace()));
  }
}
