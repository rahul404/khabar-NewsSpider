package org.kjsce.khabar.service.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetFetcherService {
    @Autowired
    private TwitterFactoryService twitterFactoryService;

    public List<Query> prepareQueryFrom(String ... fromUsers){
        List<Query> queryList = new LinkedList<>();
        for(int i = 0;i<fromUsers.length;i++){
            Query query = new Query("from:"+fromUsers[i]);
            queryList.add(query);
        }
        return queryList;
    }
    public List<Query> prepareQueryContaining (String ... keywords){
        List<Query> queryList = new LinkedList<>();
        for(int i = 0;i<keywords.length;i++){
            Query query = new Query(keywords[i]);
            queryList.add(query);
        }
        return queryList;
    }

    public List<Status> searchTweets(Query query) throws TwitterException {
        Twitter twitter = twitterFactoryService.getTwitter();
        QueryResult result = twitter.search(query);
        return result.getTweets();
    }

    public List<Status> searchTweets(String searchQuery) throws TwitterException {
        Query query = new Query(searchQuery);
        return searchTweets(query);
    }
}
