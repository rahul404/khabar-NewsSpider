package org.kjsce.khabar.service.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import twitter4j.Status;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TweetFetcherServiceTest {
    @Autowired
    private TweetFetcherService tweetFetcherService;

    @Test
    public void shouldReturnTweetsForKeywordManchester_searchTweetsIsWorking() throws Exception{
//        String searchQuery = "manchester";
//        List<String> resultTweets = tweetFetcher.searchTweets("\""+searchQuery+"\"");
        String searchQuery = "from:airnewsalerts";
        List<Status> resultTweets = tweetFetcherService.searchTweets(searchQuery);
        String op = resultTweets.get(0).getText();
        System.out.println("size = "+resultTweets.size());
        Files.write(new File("tweet.txt").toPath(),op.getBytes());
        for(Status status : resultTweets){
            ObjectMapper om = new ObjectMapper();

            System.out.println("Tweet = "+om.writeValueAsString(status));
            String tweet = status.getText().toLowerCase();
//            Assert.assertTrue(tweet.contains(searchQuery) );
        }
    }
}
