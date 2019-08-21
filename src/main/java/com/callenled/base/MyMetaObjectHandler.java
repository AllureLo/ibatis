package com.callenled.base;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: callenled
 * @Date: 19-8-20 下午7:17
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        //版本号3.0.6以及之前的版本
        //this.setFieldValByName("operator", "Jerry", metaObject);
        //@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
        Date now = new Date();
        this.setInsertFieldValByName("createTime", now, metaObject);
        this.setInsertFieldValByName("updateTime", now, metaObject);
        this.setInsertFieldValByName("isDelete", 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("start update fill ....");
        //this.setFieldValByName("operator", "Tom", metaObject);
        //@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
        this.setUpdateFieldValByName("updateTime", new Date(), metaObject);
    }
}
