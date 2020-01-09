package jb;

import com.aventstack.extentreports.ExtentTest;
import core.JBEmbedder;
import listeners.ExtentReportTestListener;
import org.jbehave.core.embedder.Embedder;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;


import java.util.Arrays;
import java.util.List;

@Listeners({ ExtentReportTestListener.class})
public class TestRunner {

    @Test
    public void runClasspathLoadedStoriesAsJUnit() {
        Embedder embedder = new JBEmbedder();
        List<String> storyPaths = Arrays.asList("stories/sampleDB.story","stories/sample1.story");
        embedder.runStoriesAsPaths(storyPaths);
    }

}