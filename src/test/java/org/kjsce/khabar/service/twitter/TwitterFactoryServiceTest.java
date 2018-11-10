package org.kjsce.khabar.service.twitter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import twitter4j.Twitter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwitterFactoryServiceTest {
    @Autowired
    private TwitterFactoryService twitterFactoryService;

    @Test
    public void shouldNotThrowException_Twitter4jIsWorking(){
        Twitter twitter = twitterFactoryService.getTwitter();
        Assert.assertNotNull(twitter.list());
    }
}
