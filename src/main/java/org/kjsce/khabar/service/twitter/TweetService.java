package org.kjsce.khabar.service.twitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kjsce.khabar.exception.InterServiceCallFailedException;
import org.kjsce.khabar.exception.NoSuchTweetException;
import org.kjsce.khabar.model.twitter.TweetEntity;
import org.kjsce.khabar.repository.TweetEntityRepository;
import org.kjsce.khabar.utils.client.CrudServiceClient;
import org.kjsce.khabar.utils.client.LocationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;

import java.io.IOException;
import java.util.List;

@Service
public class TweetService {
    @Autowired
    private TweetEntityRepository tweetEntityRepository;

    @Autowired
    private CrudServiceClient crudServiceClient;

    @Autowired
    private LocationClient locationClient;

    public TweetEntity findById(Long id){
        TweetEntity tweetEntity = tweetEntityRepository.findById(id).orElse(null);
        if(tweetEntity == null){
            throw new NoSuchTweetException(id);
        }
        return tweetEntity;
    }

    public Iterable<TweetEntity> findTop10(){
        return tweetEntityRepository.findTop10ByOrderByIdDesc();
    }
    public Iterable<TweetEntity> findOlder10(Long fromId){
        return tweetEntityRepository.findTop10ByIdLessThanOrderByIdDesc(fromId);
    }
    public Iterable<TweetEntity> findYounger10(Long fromId){
        return tweetEntityRepository.findTop10ByIdGreaterThanOrderById(fromId);
    }

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
            try{
                List<String> locations = locationClient.getLocations(tweetEntity.getText());
                System.out.println("saved = "+crudServiceClient.save(tweetEntity, locations));
            }
            catch (IOException | InterServiceCallFailedException e){
                System.out.println("error in storing tweet");
                e.printStackTrace();
            }
        }
    }
    public Iterable<TweetEntity> findAll(){
        return tweetEntityRepository.findAll();
    }
    public TweetEntity create(Status status) {
        try{
            return new TweetEntity.Builder().build(status);
        }
        catch (JsonProcessingException e){
            return null;
        }
    }
}
