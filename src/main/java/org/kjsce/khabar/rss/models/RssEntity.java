package org.kjsce.khabar.rss.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.kjsce.khabar.model.BaseEntity;
import org.kjsce.khabar.utils.StringUtils;

import javax.persistence.*;
import java.util.Set;

@EntityListeners(RssEntity.RssEntityListener.class)
@Entity
public class RssEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private Set<RssItem> itemSet;

    @Column(unique = true, length = 255)
    private String base_url;

    private String description;

    @JsonIgnore
    @Column(unique = true)
    private String hashCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<RssItem> getItemSet() {
        return itemSet;
    }

    public void setItemSet(Set<RssItem> itemSet) {
        this.itemSet = itemSet;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public static class RssEntityListener{
        @PrePersist
        @PreUpdate
        public void generateHash(final RssEntity entity){
            entity.setHashCode(StringUtils.getSHA256(entity.getBase_url()));
        }
    }

}
