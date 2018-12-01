package org.kjsce.khabar.service.twitter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kjsce.khabar.service.classifier.TweetNewsIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TweetNewsIdentifierTest {
    @Autowired
    TweetNewsIdentifier tweetNewsIdentifier;

    @Test
    public void shouldClassifyNews(){
        String tweet = "stampede at elphistone road.";
        tweetNewsIdentifier.isNews(tweet);

        tweet = "I went to Waterstones and asked if they had any books on turtles?\n" +
                "\n" +
                "\"Hardback?”\n" +
                "\n" +
                "\"Yes, with little legs.\"";
        tweetNewsIdentifier.isNews(tweet);
    }
}
