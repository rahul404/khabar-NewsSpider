package org.kjsce.khabar.model.twitter;

import org.kjsce.khabar.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import twitter4j.Status;

@Document(collection = "tweet")
public class TweetEntity extends BaseEntity {
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
