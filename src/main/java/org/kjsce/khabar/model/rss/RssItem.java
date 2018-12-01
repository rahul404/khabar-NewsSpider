package org.kjsce.khabar.model.rss;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.kjsce.khabar.model.BaseEntity;
import org.kjsce.khabar.utils.StringUtils;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
@EntityListeners(RssItem.RssItemEntityListener.class)
@Entity
public class RssItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;/** A database id**/

    private String title;/** The title of this item **/

    @Column(unique = true, length = 500)
    private String link;/** Stores the link of the RssItem**/

    @Column(length = 1024)
    private String description;/** A brief description about this item **/

    private String guid;/** Stores the link of the RssItem NOTE- apparently theres no difference bw a guid and the link
     for the moment... this behavior might change later**/

    @JsonIgnore
    @Column(unique = true)
    private String hashCode;/** The hashcode of the contents of this url **/

    @CreatedDate
    private java.util.Date pubDate;/** Date of publication of this RssItem**/

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RssEntity rssEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public RssEntity getRssEntity() {
        return rssEntity;
    }

    public void setRssEntity(RssEntity rssEntity) {
        this.rssEntity = rssEntity;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public static class RssItemEntityListener{
        @PrePersist
        @PreUpdate
        public void generateHash(final RssItem rssItemRef){
            rssItemRef.setHashCode(StringUtils.getSHA256(rssItemRef.link));
        }


    }


}
