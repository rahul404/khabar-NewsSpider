package org.kjsce.khabar.service.twitter;

import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TwitterFactoryService {
    public static final String CONSUMER_KEY = "rpRwvyYDer31Y7fo7p41LadAZ";
    public static final String CONSUMER_SECRET= "Zn9Z3dK8OhEarWixGlONsRFiv0TwwMEQSlflacQo2cpZ4E4QXg ";
    public static final String ACCESS_TOKEN = "746082684762394625-9TYAtfy6XGBiTjF7i4YP0OVo3VBMXDO";
    public static final String ACCESS_TOKEN_SECRET= "C1Yz8DhzAC2fFKvriiqhMp8Wr2Yu210PkGlT5mp54iQJz";

    private final Twitter twitter ;
    TwitterFactoryService(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public Twitter getTwitter() {
        return twitter;
    }
}
