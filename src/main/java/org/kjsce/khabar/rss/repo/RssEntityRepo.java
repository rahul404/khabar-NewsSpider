package org.kjsce.khabar.rss.repo;

import org.kjsce.khabar.rss.models.RssEntity;
import org.springframework.data.repository.CrudRepository;

public interface RssEntityRepo extends CrudRepository<RssEntity, Long> {
    public RssEntity getByHashCode(String hashCode);
}
