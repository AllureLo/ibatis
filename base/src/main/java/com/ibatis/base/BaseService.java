package com.ibatis.base;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @Author: ibatis
 * @Date: 19-8-16 下午6:47
 */
public interface BaseService<T extends BaseModel> {

    /**
     * 存在即更新(根据主键)
     *
     * @param entity 待保存的实体类
     */
    void save(T entity);

    /**
     * 新增
     *
     * @param entity 待新增的实体类
     */
    void insert(T entity);

    /**
     * 批量新增
     *
     * @param entities 待新增的实体列表
     */
    void insertBatch(List<T> entities);

    /**
     * 修改
     *
     * @param entity 待修改的实体类
     */
    void updateById(T entity);

    /**
     * 根据主键逻辑删除
     *
     * @param id 主键id
     */
    void deleteById(Long id);

    /**
     * 根据主键批量逻辑删除
     *
     * @param ids 主键id列表
     */
    void deleteBatchByIds(List<Long> ids);

    /**
     * 根据主键查询
     *
     * @param id 主键id
     * @return T
     */
    T selectById(Long id);

    /**
     * 条件查询单条数据
     *
     * @param entity 查询条件的实体类
     * @return T
     */
    T selectOne(T entity);

    /**
     * 条件查询数据量
     *
     * @param entity 查询条件的实体类
     * @return int
     */
    int selectCount(T entity);

    /**
     * 条件查询数据列表
     *
     * @param entity   查询条件的实体类
     * @param pageNum  当前页数
     * @param pageSize 页数大小
     * @return list
     */
    List<T> selectList(T entity, int pageNum, int pageSize);

    /**
     * 批量根据主键id查询数据列表
     *
     * @param ids 主键id列表
     * @return
     */
    List<T> selectByIds(List<Long> ids);

    /**
     * 条件查询数据列表(分页)
     *
     * @param entity   查询条件的实体类
     * @param pageNum  当前页数
     * @param pageSize 页数大小
     * @return page
     */
    IPage<T> selectForPage(T entity, int pageNum, int pageSize);
}
