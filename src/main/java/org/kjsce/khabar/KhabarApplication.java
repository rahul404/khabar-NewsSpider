package org.kjsce.khabar;

import org.kjsce.khabar.rss.RssFetcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KhabarApplication {

    public static void main(String[] args) {
        SpringApplication.run(KhabarApplication.class, args);
    }
}
