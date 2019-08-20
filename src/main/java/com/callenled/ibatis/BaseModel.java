package com.callenled.ibatis;

import com.callenled.ibatis.annotation.ColumnName;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午4:24
 */
public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = -717897301219259512L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    @ColumnName(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @ColumnName(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除
     */
    @ColumnName(value = "is_delete")
    private Integer isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    protected void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    protected void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
