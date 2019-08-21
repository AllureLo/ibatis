package com.callenled.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午6:48
 */
public abstract class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {

    /**
     * mapper类
     *
     * @return
     */
    public abstract BaseMapper<T> getBaseMapper();

    /**
     * model实体类型
     *
     * @return
     */
    public abstract Class<T> getEntityClass();

    @Override
    public void save(T object) {
        if (object.getId() == null) {
            insert(object);
        } else {
            updateById(object);
        }
    }

    @Override
    public void insert(T object) {
        getBaseMapper().insert(object);
    }

    @Override
    public void updateById(T object) {
        getBaseMapper().updateById(object);
    }

    @Override
    public void deleteById(Long id) {
        getBaseMapper().deleteById(id);
    }

    /*@Override
    public T queryById(Long id) {
        try {
            T object = getEntityClass().newInstance();
            object.setId(id);
            object.setIsDelete(0);
            return getBaseMapper().selectOne(new QueryWrapper<>(object));
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    @Override
    public T queryById(Long id) {
        return getBaseMapper().selectOne(new QueryWrapper<T>()
                .lambda()
                .eq(T::getId, id)
                .eq(T::getIsDelete, 0)
        );
    }
}
