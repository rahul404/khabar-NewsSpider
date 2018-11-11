package org.kjsce.khabar.repository;


import org.kjsce.khabar.model.twitter.TweetEntity;
import org.springframework.data.repository.CrudRepository;

public interface TweetEntityRepository extends CrudRepository<TweetEntity,Long> {

}
