package com.ibatis.base;

/**
 * @Author: ibatis
 * @Date: 19-8-16 下午4:35
 */

public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 逻辑删除(修改updateTime)
     *
     * @param id       主键id
     * @return int
     */
    int deleteByIdWithFill(Long id);
}
