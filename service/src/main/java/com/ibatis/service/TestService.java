package com.ibatis.service;

import com.ibatis.model.po.Test;
import com.ibatis.base.BaseService;

/**
 * @Author: ibatis
 * @Date: 19-8-16 下午6:54
 */
public interface TestService extends BaseService<Test> {

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
