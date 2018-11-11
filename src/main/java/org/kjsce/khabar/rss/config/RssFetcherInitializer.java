package org.kjsce.khabar.rss.config;

import org.kjsce.khabar.rss.RssFetcherThreadWrapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RssFetcherInitializer {
    @Bean
    CommandLineRunner init(RssFetcherThreadWrapper rssFetcherThreadWrapper){
        return args -> {
            rssFetcherThreadWrapper.startThreads();
        };
    }
}
