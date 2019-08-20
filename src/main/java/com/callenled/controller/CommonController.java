package com.callenled.controller;

import com.callenled.bean.Result;
import com.callenled.mapper.TestMapper;
import com.callenled.model.po.Test;
import com.callenled.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午2:31
 */
@RestController
@RequestMapping(value = "/common")
public class CommonController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Result add(@RequestParam(value = "param") String param) {
        Test test = new Test();
        test.setParam(param);
        testService.save(test);
        return Result.success(test);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(@RequestParam(value = "id") Long id) {
        Test test = testService.queryById(id);
        return Result.success(test);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Result update(@RequestParam(value = "id") Long id,
                      @RequestParam(value = "param") String param) {
        Test test = testService.queryById(id);
        test.setParam(param);
        testService.save(test);
        return Result.success(test);
    }
}
