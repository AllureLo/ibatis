package com.callenled.base;

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
    void updateById(T object);

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
     * 条件查询某条数据
     *
     * @param object
     * @return
     */
    T queryOne(T object);
}
