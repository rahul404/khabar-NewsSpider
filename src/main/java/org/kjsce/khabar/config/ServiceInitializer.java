package org.kjsce.khabar.config;

import org.kjsce.khabar.service.twitter.TwitterSpider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceInitializer {
    @Bean
    CommandLineRunner initTwitterSpider(TwitterSpider twitterSpider) {
        return args -> {
            System.out.println("\n\n\n I was executed \n\n\n");
            twitterSpider.crawl();
        };
    }
}
