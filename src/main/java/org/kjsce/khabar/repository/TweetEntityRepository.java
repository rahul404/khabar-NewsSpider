package org.kjsce.khabar.repository;


import org.kjsce.khabar.model.twitter.TweetEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetEntityRepository extends MongoRepository<TweetEntity,String> {

}
