package org.kjsce.khabar.repository;


import org.kjsce.khabar.model.twitter.TweetEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TweetEntityRepository extends CrudRepository<TweetEntity,Long> {
    Optional<TweetEntity> findByTweetId(String tweetId);

    Iterable<TweetEntity> findTop10ByOrderByIdDesc();
    Iterable<TweetEntity> findTop10ByIdLessThanOrderByIdDesc(Long fromId);

    Iterable<TweetEntity> findTop10ByIdGreaterThanOrderById(Long fromId);
}
