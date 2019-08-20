package com.callenled.service;

import com.callenled.ibatis.BaseModel;

import java.util.List;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午6:47
 */
public interface BaseService<T extends BaseModel> {

    /**
     * 存在即更新(根据主键)
     *
     * @param object   待保存的实体类
     */
    void save(T object);

    /**
     * 新增
     *
     * @param object   待新增的实体类
     */
    void insert(T object);

    /**
     * 修改
     *
     * @param object   待修改的实体类
     */
    void update(T object);

    /**
     * 批量新增
     *
     * @param list     待新增的实体列表
     */
    void insertBatch(List<T> list);

    /**
     * 根据主键查询
     *
     * @param id       主键id的值
     * @return
     */
    T queryById(Long id);

    /**
     * 根据某个字段查询唯一数据
     *
     * @param name     字段名称
     * @param value    对应的值
     * @return
     */
    T queryOneByName(String name, Object value);

    /**
     * 逻辑删除
     *
     * @param object    待删除的实体类
     */
    void deleteById(T object);

    /**
     * 逻辑删除
     *
     * @param id       主键id的值
     */
    void deleteById(Long id);

    /**
     * 根据某个字段删除数据
     * 逻辑删除
     *
     * @param name     字段名称
     * @param value    对应的值
     */
    void deleteByName(String name, Object value);

    /**
     * 删除
     *
     * @param id       主键id的值
     * @param isDelete 是否物理删除/true:物理删除
     */
    void deleteById(Long id, boolean isDelete);

    /**
     * 删除
     *
     * @param name     字段名称
     * @param value    对应的值
     * @param isDelete 是否物理删除/true:物理删除
     */
    void deleteByName(String name, Object value,  boolean isDelete);

    /**
     * 查询数量
     *
     * @param object   待查询的实体类
     * @return
     */
    int queryCount(T object);

    /**
     * 查询列表
     *
     * @param object   待查询的实体类
     * @param start    页码          默认第一页
     * @param limit    每页数据量     默认每页10条数据
     * @return
     */
    List<T> queryList(T object, Integer start, Integer limit);

    /**
     * 查询数量
     *
     * @param object   待查询的实体类
     * @param likeProperties 相似查询条件
     * @return
     */
    int queryUseLikeCount(T object, List<String> likeProperties);

    /**
     * 查询列表
     *
     * @param object   待查询的实体类
     * @param start    页码          默认第一页
     * @param limit    每页数据量     默认每页10条数据
     * @param likeProperties 相似查询条件
     * @return
     */
    List<T> queryUseLikeList(T object, List<String> likeProperties, Integer start, Integer limit);

    /**
     * 查询数量
     *
     * @param object   待查询的实体类
     * @param userPara 自定义条件
     * @return
     */
    int queryCustomizeCount(T object, List<String> userPara);

    /**
     * 查询列表
     *
     * @param object   待查询的实体类
     * @param start    页码          默认第一页
     * @param limit    每页数据量     默认每页10条数据
     * @param userPara 自定义条件
     * @return
     */
    List<T> queryCustomizeList(T object, List<String> userPara, Integer start, Integer limit);

    /**
     * 查询数量
     *
     * @param object   待查询的实体类
     * @param userPara 自定义条件
     * @param likeProperties 相似查询条件
     * @return
     */
    int queryCount(T object, List<String> likeProperties, List<String> userPara);

    /**
     * 查询列表
     *
     * @param object   待查询的实体类
     * @param start    页码          默认第一页
     * @param limit    每页数据量     默认每页10条数据
     * @param userPara 自定义条件
     * @param likeProperties 相似查询条件
     * @return
     */
    List<T> queryList(T object, List<String> likeProperties, List<String> userPara, Integer start, Integer limit);
}
