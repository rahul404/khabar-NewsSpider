package org.kjsce.khabar.model.twitter;

import org.kjsce.khabar.model.BaseEntity;
import twitter4j.Status;

import javax.persistence.*;

@Entity
@Table(name= "tweet")
public class TweetEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
