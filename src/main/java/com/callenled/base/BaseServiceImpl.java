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

    @Override
    public T queryById(Long id) {
        T entity;
        try {
            entity = getEntityClass().newInstance();
            entity.setId(id);
            entity.setIsDelete(0);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return queryOne(entity);
    }

    @Override
    public T queryOne(T entity) {
        if (entity == null) {
            throw new RuntimeException("entity can not be null");
        }
        return mapper.selectOne(new QueryWrapper<>(entity));
    }
}
