package com.ibatis.mapper;

import com.ibatis.base.BaseMapper;
import com.ibatis.entity.po.TestTwo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Author: ibatis
 * @Date: 19-8-16 下午4:35
 */
@CacheNamespace
public interface TestTwoMapper extends BaseMapper<TestTwo> {

}
