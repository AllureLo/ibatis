package com.callenled.test;

import com.ibatis.mapper.TestMapper;
import com.ibatis.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: ibatis
 * @Date: 19-8-21 下午2:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.ibatis.mapper")
public class TestServiceTest {

    @Autowired
    private TestService testService;

    @Autowired
    private TestMapper testMapper;

    @Test
    public void queryById() {
        long startTime = System.currentTimeMillis();
        com.ibatis.entity.po.Test test = testService.queryById(7L);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

}