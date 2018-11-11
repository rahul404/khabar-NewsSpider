package org.kjsce.khabar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.data.annotation.*;

import javax.annotation.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;


public abstract class BaseEntity {

    @JsonIgnore
    @CreatedDate
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date updatedDate;

    @JsonIgnore
    @Version
    private Long version;

    @JsonIgnore
    private Boolean delete = Boolean.FALSE;



    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}
