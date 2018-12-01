package org.kjsce.khabar.controller.twitter;

import org.kjsce.khabar.exception.NoSuchTweetException;
import org.kjsce.khabar.model.twitter.TweetEntity;
import org.kjsce.khabar.service.twitter.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news-spider/twitter")
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTweetEntity(@PathVariable(name = "id") Long id){
        try{
            TweetEntity tweetEntity = tweetService.findById(id);
            return ResponseEntity.ok(tweetEntity);
        }
        catch (NoSuchTweetException nste){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nste.getMessage());
        }
    }

    @GetMapping("/getTweets")
    public ResponseEntity<?> getTweets(@RequestParam(name = "from-id",required = false) Long fromId,
                                       @RequestParam(name = "old",required = false) Integer old){
        if(fromId == null){
            return ResponseEntity.ok(tweetService.findTop10());
        }
        else {
            if(old == 1){
                return ResponseEntity.ok(tweetService.findOlder10(fromId));
            }
            else {
                return ResponseEntity.ok(tweetService.findYounger10(fromId));
            }
        }
    }
}

