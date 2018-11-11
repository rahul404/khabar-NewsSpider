package org.kjsce.khabar.rss.services;

import org.kjsce.khabar.rss.ResourceNotFoundException;
import org.kjsce.khabar.rss.models.RssEntity;
import org.kjsce.khabar.rss.repo.RssEntityRepo;
import org.kjsce.khabar.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RssEntityService {

    @Autowired
    private RssEntityRepo repo;


    public RssEntity createEntity(RssEntity entity){
        RssEntity ent =this.repo.getByHashCode(StringUtils.getSHA256(entity.getBase_url()));
        return ent == null?this.repo.save(entity):ent;
    }


    public RssEntity findById(Long id){
       return this.repo.findById(id).orElseThrow(()->new ResourceNotFoundException("RssEntity", "id", id+""));
    }
}
