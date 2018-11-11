package org.kjsce.khabar.service.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kjsce.khabar.model.twitter.TweetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import twitter4j.Status;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TweetServiceTest {
    @Autowired
    private TweetService tweetService;
    @Autowired
    private TweetFetcherService tweetFetcherService;

    @Test
    public void shouldSaveTweet_saveIsWorking() throws Exception{
        String searchQuery = "from:rahulahuja404";
        List<Status> resultTweets = tweetFetcherService.searchTweets(searchQuery);
        String op = resultTweets.get(0).getText();
        System.out.println("size = "+resultTweets.size());
        for(Status status : resultTweets){
            ObjectMapper om = new ObjectMapper();
            TweetEntity tweetEntity = new TweetEntity();
            tweetEntity.setStatus(om.writeValueAsString(status));
            tweetEntity.setTweetId(status.getId()+"");
            tweetService.save(tweetEntity);

            System.out.println("Tweet = "+om.writeValueAsString(status));
            String tweet = status.getText().toLowerCase();
//            Assert.assertTrue(tweet.contains(searchQuery) );
        }
    }
}
