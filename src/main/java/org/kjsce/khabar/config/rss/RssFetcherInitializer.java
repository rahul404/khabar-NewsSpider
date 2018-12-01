package org.kjsce.khabar.config.rss;

import org.kjsce.khabar.exception.rss.RssFetcherThreadWrapper;
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
