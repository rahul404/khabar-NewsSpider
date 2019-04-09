package org.kjsce.khabar.service.twitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.JDBCException;
import org.kjsce.khabar.exception.InterServiceCallFailedException;
import org.kjsce.khabar.model.twitter.TweetEntity;
import org.kjsce.khabar.service.Crawler;
import org.kjsce.khabar.service.classifier.TopicIdentifier;
import org.kjsce.khabar.service.preprocessor.BagOfWordsService;
import org.kjsce.khabar.utils.client.ClassifyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class TwitterSpider implements Crawler {
    @Autowired
    private TweetFetcherService tweetFetcherService;
    @Autowired
    private TweetService tweetService;
    @Autowired
    private TopicIdentifier topicIdentifier;
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
                                ClassifyResponse classifyResponse = topicIdentifier.
                                        classifyUsingTextRazor(tweetEntity.getText());
                                if(topicIdentifier.isNews(classifyResponse)){
                                    System.out.println("Tweet id = "+tweetEntity.getTweetId());
                                    System.out.println("Tweet text = "+tweetEntity.getText());
                                    tweetService.checkAndSave(status);
                                }
                            }
                        }
                        catch (TwitterException | IOException | JDBCException |
                                InterServiceCallFailedException e){
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
