package jb;

import core.JBEmbedder;
import org.jbehave.core.embedder.Embedder;
import org.junit.Test;


import java.util.Arrays;
import java.util.List;

public class TestRunner {

    @Test
    public void runClasspathLoadedStoriesAsJUnit() {
        Embedder embedder = new JBEmbedder();
        List<String> storyPaths = Arrays.asList("stories/sampleDB.story","stories/sample1.story");
        embedder.runStoriesAsPaths(storyPaths);
    }

}