package com.ibatis.mapper;

import com.ibatis.entity.po.Test;
import com.ibatis.base.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Author: ibatis
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

    /**
     * 自写的根据主键查询语句
     *
     * @param id
     * @return
     */
    Test selfSelectById(Long id);
}
