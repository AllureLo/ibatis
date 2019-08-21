package com.callenled.base;

import com.baomidou.mybatisplus.annotation.*;
import org.apache.ibatis.type.JdbcType;

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
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill=FieldFill.INSERT, jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill=FieldFill.INSERT_UPDATE, jdbcType = JdbcType.TIMESTAMP)
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete", fill=FieldFill.INSERT, jdbcType = JdbcType.INTEGER)
    @TableLogic(value = "0", delval = "1")
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
