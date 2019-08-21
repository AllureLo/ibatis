package com.callenled.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午6:48
 */
public abstract class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {

    @Autowired
    private BaseMapper<T> mapper;

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
        mapper.insert(object);
    }

    @Override
    public void updateById(T object) {
        mapper.updateById(object);
    }

    @Override
    public void deleteById(Long id) {
        mapper.deleteById(id);
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
        return mapper.selectOne(new QueryWrapper<T>()
                .lambda()
                .eq(T::getIsDelete, 0)
        );
    }
}
