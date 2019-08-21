package com.callenled.base;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午6:47
 */
public interface BaseService<T extends BaseModel> {

    /**
     * 存在即更新(根据主键)
     *
     * @param entity   待保存的实体类
     */
    void save(T entity);

    /**
     * 新增
     *
     * @param entity   待新增的实体类
     */
    void insert(T entity);

    /**
     * 修改
     *
     * @param entity   待修改的实体类
     */
    void updateById(T entity);

    /**
     * 根据主键逻辑删除
     *
     * @param id       主键id
     */
    void deleteById(Long id);

    /**
     * 根据主键查询
     *
     * @param id       主键id
     * @return T
     */
    T queryById(Long id);

    /**
     * 条件查询单条数据
     *
     * @param entity   查询条件的实体类
     * @return T
     */
    T queryOne(T entity);

    /**
     * 条件查询数据量
     *
     * @param entity   查询条件的实体类
     * @return int
     */
    int queryCount(T entity);

    /**
     * 条件查询数据列表
     *
     * @param entity   查询条件的实体类
     * @return list
     */
    List<T> queryList(T entity);

    /**
     * 条件查询数据列表(分页)
     *
     * @param entity   查询条件的实体类
     * @param pageNum  当前页数
     * @param pageSize 页数大小
     * @return page
     */
    IPage<T> queryPage(T entity, int pageNum, int pageSize);
}
