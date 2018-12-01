package org.kjsce.khabar.controller.rss;

import org.kjsce.khabar.service.rss.RssItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
@RequestMapping("/api/v1/rss/items/")
public class RssItemController {

    @Autowired
    private RssItemService rssItemService;

    @GetMapping("latest/")
    public ResponseEntity<Iterator<?>> getLatestRssItems(){
        return ResponseEntity.ok().body(this.rssItemService.getLatestItems());
    }

    @GetMapping("less/{id}/")
    public ResponseEntity<Iterator<?>> getRssItemsLessThan(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.rssItemService.getItemsLessThanId(id));
    }

    @GetMapping("greater/{id}/")
    public ResponseEntity<Iterator<?>> getRssItemsGreaterThan(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.rssItemService.getItemGreaterThanId(id));

    }


}
