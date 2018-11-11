package org.kjsce.khabar.service.twitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.JDBCException;
import org.kjsce.khabar.service.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;

@Service
public class TwitterSpider implements Crawler {
    @Autowired
    private TweetFetcherService tweetFetcherService;
    @Autowired
    private TweetService tweetService;
    private long interval = DEFAULT_INTERVAL;
    private boolean hasStarted  = false;
    private boolean hasStopped = false;
    public static long DEFAULT_INTERVAL = 1000*10;

    @Override
    public void crawl(){
        if(hasStarted){
            throw new IllegalArgumentException("cannot start a crawler after it has been stopped");
        }
        hasStarted = true;
        while (!hasStopped){
            try{
                List<Query> queryList = tweetFetcherService
                        .prepareQueryContaining(TwitterSourceConstants.NEWS_ACCOUNTS);
                for (Query query : queryList ){
                    try{
                        List<Status> statusList = tweetFetcherService.searchTweets(query);
                        for(Status status : statusList){
                            tweetService.save(status);
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