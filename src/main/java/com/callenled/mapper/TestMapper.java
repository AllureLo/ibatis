package com.callenled.mapper;

import com.callenled.base.BaseMapper;
import com.callenled.model.po.Test;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午4:35
 */

public interface TestMapper extends BaseMapper<Test> {

    Test findById(@Param("id") Long id);

}
