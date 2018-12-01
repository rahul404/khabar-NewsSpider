package org.kjsce.khabar.repository.rss;

import org.kjsce.khabar.model.rss.RssEntity;
import org.springframework.data.repository.CrudRepository;

public interface RssEntityRepo extends CrudRepository<RssEntity, Long> {
    public RssEntity getByHashCode(String hashCode);
}
