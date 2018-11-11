package org.kjsce.khabar.model.twitter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kjsce.khabar.model.BaseEntity;
import twitter4j.Status;

import javax.persistence.*;

@Entity
@Table(name= "tweet")
public class TweetEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String  tweetId;
    private String text;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String  getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class Builder{
        public TweetEntity build(Status status) throws JsonProcessingException {
            TweetEntity tweetEntity = new TweetEntity();
            String text = status.getText();
            if(status.isRetweet()){
                Status temp = status;
                while(true){
                    temp = temp.getRetweetedStatus();
                    if(temp == null){
                        break;
                    }
                    text = temp.getText();
                }
            }
            System.out.println("Tweet id = "+status.getId()+" size = "+status.getId());
            tweetEntity.setTweetId(status.getId()+"");
            System.out.println("Tweet text = "+text +" size = "+text.length());
            tweetEntity.setText(text);
            tweetEntity.setStatus(new ObjectMapper().writeValueAsString(status));
            return tweetEntity;
        }
    }
}
