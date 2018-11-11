package org.kjsce.khabar.service.twitter;

import org.kjsce.khabar.model.twitter.TweetEntity;
import org.kjsce.khabar.repository.TweetEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {
    @Autowired
    private TweetEntityRepository tweetEntityRepository;

    public void save(TweetEntity tweetEntity){
        tweetEntityRepository.save(tweetEntity);
    }
    public List<TweetEntity> findAll(){
        return tweetEntityRepository.findAll();
    }
}
