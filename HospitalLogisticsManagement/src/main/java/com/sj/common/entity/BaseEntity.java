package com.sj.common.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import java.util.Date;

/**
 * Created by wa on 2016/1/11.
 */
@MappedSuperclass
public class BaseEntity {

    @Id
    @Column(name = "ID_")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    /**
     * 是否能用
     */
    @Column(name = "ENABLE_")
    private Boolean enable;
  
    @Column(name = "CREATE_STAMP_")
    private Date createStamp;

    @Column(name = "MODIFY_STAMP_")
    private Date modifyStamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    public Date getCreateStamp() {
        return createStamp;
    }

    public void setCreateStamp(Date createStamp) {
        this.createStamp = createStamp;
    }

    public Date getModifyStamp() {
        return modifyStamp;
    }

    public void setModifyStamp(Date modifyStamp) {
        this.modifyStamp = modifyStamp;
    }
}
