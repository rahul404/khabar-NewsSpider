package org.kjsce.khabar.service.twitter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwitterSpiderTest {
    @Autowired
    private TwitterSpider twitterSpiderService;

    @Test
    public void shouldStartAndStop() throws Exception{
        twitterSpiderService.crawl();
        Thread.sleep(1000*10);
        twitterSpiderService.stop();
    }
}
