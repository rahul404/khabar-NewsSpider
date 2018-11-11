package org.kjsce.khabar.service.twitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kjsce.khabar.model.twitter.TweetEntity;
import org.kjsce.khabar.repository.TweetEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;

import java.util.List;

@Service
public class TweetService {
    @Autowired
    private TweetEntityRepository tweetEntityRepository;

    public void save(TweetEntity tweetEntity){
        tweetEntityRepository.save(tweetEntity);
    }

    public void save(Status status) throws JsonProcessingException {
        TweetEntity tweetEntity = new TweetEntity.Builder().build(status);
        tweetEntityRepository.save(tweetEntity);
    }
    public void checkAndSave(Status status) throws JsonProcessingException{
        TweetEntity tweetEntity = new TweetEntity.Builder().build(status);
        if(tweetEntityRepository.findByTweetId(tweetEntity.getTweetId()).orElse(null) == null){
            tweetEntityRepository.save(tweetEntity);
        }
    }
    public Iterable<TweetEntity> findAll(){
        return tweetEntityRepository.findAll();
    }
}
