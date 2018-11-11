package org.kjsce.khabar.rss.services;

import org.kjsce.khabar.rss.ResourceNotFoundException;
import org.kjsce.khabar.rss.models.RssEntity;
import org.kjsce.khabar.rss.models.RssItem;
import org.kjsce.khabar.rss.repo.RssEntityRepo;
import org.kjsce.khabar.rss.repo.RssItemRepo;
import org.kjsce.khabar.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class RssItemService {

    @Autowired
    private RssItemRepo itemRepo;

    @Autowired
    private RssEntityService rssEntityService;

    public RssItem getById(Long id){
        return this.itemRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("RssItem", "id", id+""));
    }

    public Iterator<RssItem> getByRssEntity(Long rssEntityId){
        RssEntity entity = this.rssEntityService.findById(rssEntityId);
        return this.itemRepo.findAllByRssEntity(entity);
    }

    public RssItem createRssItem(Long rssEntityId, RssItem rssItem){
        RssEntity entity = this.rssEntityService.findById(rssEntityId);

        if(entity != null) {
            RssItem item = this.itemRepo.getByHashCode(StringUtils.getSHA256(rssItem.getLink()));

            rssItem.setRssEntity(entity);
            return item == null?this.itemRepo.save(rssItem):item;
        }
        return null;
    }

    public Iterator<RssItem> getLatestItems(){
        //defaults-> order by desc, limit 10
        return this.itemRepo.findLatestRssItems().iterator();
    }

    public Iterator<RssItem> getItemsLessThanId(Long id){
        return this.itemRepo.getLessThan(id).iterator();
    }

    public Iterator<RssItem> getItemGreaterThanId(Long id){
        return this.itemRepo.getGreaterThan(id).iterator();
    }



}
