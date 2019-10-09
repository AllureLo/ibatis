package com.ibatis.base.handler;

import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: ibatis
 * @Date: 19-8-20 下午7:17
 */
@Component
public class MetaObjectHandler implements com.baomidou.mybatisplus.core.handlers.MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        //@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
        Date now = new Date();
        this.setInsertFieldValByName("createTime", now, metaObject);
        this.setInsertFieldValByName("updateTime", now, metaObject);
        this.setInsertFieldValByName("isDelete", 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("start update fill ....");
        //@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
        this.setUpdateFieldValByName("updateTime", new Date(), metaObject);
    }
}
