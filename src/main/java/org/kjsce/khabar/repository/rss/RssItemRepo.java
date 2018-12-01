package org.kjsce.khabar.repository.rss;

import org.kjsce.khabar.model.rss.RssEntity;
import org.kjsce.khabar.model.rss.RssItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;

public interface RssItemRepo extends CrudRepository<RssItem, Long> {
    public Iterator<RssItem> findAllByRssEntity(RssEntity rssEntityId);

    public RssItem getByHashCode(String hashCode);

    @Query(value = "select * from rss_item rss order by rss.id desc limit 0,10;",nativeQuery = true)
    public Iterable<RssItem> findLatestRssItems();

    @Query(value="select * from rss_item where id < ?1 order by id desc limit 0, 10", nativeQuery = true)
    public Iterable<RssItem> getLessThan(Long id);


    @Query(value="select * from rss_item where id > ?1 order by id desc limit 0, 10", nativeQuery = true)
    public Iterable<RssItem> getGreaterThan(Long id);
}
