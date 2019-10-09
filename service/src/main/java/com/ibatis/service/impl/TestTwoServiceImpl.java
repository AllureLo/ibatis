package com.ibatis.service.impl;

import com.ibatis.base.BaseMapper;
import com.ibatis.base.BaseServiceImpl;
import com.ibatis.mapper.TestTwoMapper;
import com.ibatis.entity.po.TestTwo;
import com.ibatis.service.TestTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: ibatis
 * @Date: 19-8-16 下午6:54
 */
@Service(value = "testTwoService")
public class TestTwoServiceImpl extends BaseServiceImpl<TestTwo> implements TestTwoService {

    @Autowired
    private TestTwoMapper testTwoMapper;

    @Override
    public BaseMapper<TestTwo> mapper() {
        return testTwoMapper;
    }
}
