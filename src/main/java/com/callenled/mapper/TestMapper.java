package com.callenled.mapper;

import com.callenled.base.BaseMapper;
import com.callenled.model.po.Test;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午4:35
 */
@CacheNamespace
public interface TestMapper extends BaseMapper<Test> {

    /**
     * 自写的修改语句
     *
     * @param entity
     */
    void selfUpdateById(Test entity);
}
