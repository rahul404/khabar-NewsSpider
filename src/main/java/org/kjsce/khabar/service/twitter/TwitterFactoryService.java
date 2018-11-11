package org.kjsce.khabar.service.twitter;

import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TwitterFactoryService {
    public static final String CONSUMER_KEY = "wa1ZOeLwKF3gOqdxaaReX41dG";
    public static final String CONSUMER_SECRET= "4WIxLps8saQAX6eOtO0prG9906bMG4giG58bGBjWugzKpimgXq";
    public static final String ACCESS_TOKEN = "746082684762394625-ielmODgaYrfbSqBviEc7YLe9cpc6nrX";
    public static final String ACCESS_TOKEN_SECRET= "ctKm8Sj7ucm1p80vvY7TKh0ViJtbkoDCYGui4dMWJUYPk";

    private final Twitter twitter ;
    TwitterFactoryService(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET)
                .setTweetModeExtended(true);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public Twitter getTwitter() {
        return twitter;
    }
}
