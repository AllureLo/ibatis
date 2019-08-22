package com.ibatis.service.impl;

import com.ibatis.model.po.Test;
import com.ibatis.base.BaseMapper;
import com.ibatis.base.BaseServiceImpl;
import com.ibatis.mapper.TestMapper;
import com.ibatis.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: ibatis
 * @Date: 19-8-16 下午6:54
 */
@Service(value = "testService")
public class TestServiceImpl extends BaseServiceImpl<Test> implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public BaseMapper<Test> mapper() {
        return testMapper;
    }

    @Override
    public void selfUpdateById(Test entity) {
        testMapper.selfUpdateById(entity);
    }

    @Override
    public Test selfSelectById(Long id) {
        return testMapper.selfSelectById(id);
    }
}
