package com.ibatis.controller;

import com.ibatis.base.BaseModel;
import com.ibatis.bean.Result;
import com.ibatis.entity.po.Test;
import com.ibatis.entity.po.TestTwo;
import com.ibatis.service.TestService;
import com.ibatis.service.TestTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ibatis
 * @Date: 19-8-16 下午2:31
 */
@RestController
@RequestMapping(value = "/common")
public class CommonController {

    @Autowired
    private TestService testService;

    @Autowired
    private TestTwoService testTwoService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Result add(@RequestParam(value = "param") String param ,
                      @RequestParam(value = "type", required = false, defaultValue = "0") Integer type) {
        if (type == 0) {
            Test test = new Test();
            test.setParam(param);
            testService.save(test);
        } else {
            TestTwo testTwo = new TestTwo();
            testTwo.setParam(param);
            testTwoService.save(testTwo);
        }
        return Result.success();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(@RequestParam(value = "id") Long id,
                      @RequestParam(value = "type", required = false, defaultValue = "0") Integer type) {
        BaseModel baseModel;
        if (type == 0) {
            baseModel = testService.queryById(id);
        } else {
            baseModel = testTwoService.queryById(id);
        }
        return Result.success(baseModel);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result delete(@RequestParam(value = "id") Long id) {
        testService.deleteById(id);
        return Result.success();
    }

    @RequestMapping(value = "/deleteBatch", method = RequestMethod.GET)
    public Result deleteBatch(@RequestParam(value = "ids") String ids) {
        String[] idArray = ids.split(",");
        List<Long> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Long.parseLong(id));
        }
        testService.deleteBatchByIds(idList);
        return Result.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Result update(@RequestParam(value = "id") Long id,
                         @RequestParam(value = "param") String param) {
        Test test = new Test();
        test.setId(id);
        test.setParam(param);
        testService.updateById(test);
        return Result.success(test);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Result getList(@RequestParam(value = "pageNum") Integer pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "6") Integer pageSize) {
        return Result.success(testService.queryPage(null, pageNum, pageSize));
    }
}
