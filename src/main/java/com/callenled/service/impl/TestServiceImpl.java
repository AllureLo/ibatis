package com.callenled.service.impl;

import com.callenled.base.BaseMapper;
import com.callenled.base.BaseServiceImpl;
import com.callenled.mapper.TestMapper;
import com.callenled.model.po.Test;
import com.callenled.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: callenled
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
}
