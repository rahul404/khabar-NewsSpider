package org.kjsce.khabar.service.twitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.JDBCException;
import org.kjsce.khabar.model.twitter.TweetEntity;
import org.kjsce.khabar.service.Crawler;
import org.kjsce.khabar.service.preprocessor.BagOfWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class TwitterSpider implements Crawler {
    @Autowired
    private TweetFetcherService tweetFetcherService;
    @Autowired
    private TweetService tweetService;
    @Autowired
    private BagOfWordsService bagOfWordsService;
    private long interval = DEFAULT_INTERVAL;
    private boolean hasStarted  = false;
    private boolean hasStopped = false;
    public static long DEFAULT_INTERVAL = 1000*65;

    @Override
    public void crawl(){
        if(hasStarted){
            throw new IllegalArgumentException("cannot start a crawler after it has been stopped");
        }
        hasStarted = true;
        Runnable runnable = () -> {
            List<Query> queryList = tweetFetcherService
                    .prepareQueryContaining(TwitterSourceConstants.NEWS_ACCOUNTS);
            while (!hasStopped){
                try{
                    for (Query query : queryList ){
                        try{
                            List<Status> statusList = tweetFetcherService.searchTweets(query);
                            for(Status status : statusList){
                                TweetEntity tweetEntity = tweetService.create(status);
                                if(bagOfWordsService.isRequiredTweet(tweetEntity.getText())){
                                    tweetService.checkAndSave(status);
                                }
                            }
                        }
                        catch (TwitterException | JsonProcessingException | JDBCException e){
                            System.out.println(e);
                            e.printStackTrace();
                        }
                    }
                    Thread.sleep(interval);
                }
                catch (InterruptedException ie){
                    System.out.println(ie);
                    ie.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public void stop(){
        if(hasStopped){
            throw new IllegalArgumentException("cannot stop a crawler after it has been stopped");
        }
        hasStopped = true;
    }
    public long getInterval() {
        return interval;
    }
}
