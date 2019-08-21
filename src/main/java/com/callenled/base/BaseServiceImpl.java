package com.callenled.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午6:48
 */
public abstract class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {

    /**
     * model实体类型
     *
     * @return
     */
    public abstract Class<T> getEntityClass();

    @Autowired
    private BaseMapper<T> mapper;

    @Override
    public void save(T entity) {
        if (entity.getId() == null) {
            insert(entity);
        } else {
            updateById(entity);
        }
    }

    @Override
    public void insert(T entity) {
        mapper.insert(entity);
    }

    @Override
    public void updateById(T entity) {
        mapper.updateById(entity);
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
        entity.setIsDelete(0);
        return mapper.selectOne(new QueryWrapper<>(entity));
    }

    @Override
    public int queryCount(T entity) {
        return mapper.selectCount(new QueryWrapper<>(entity));
    }

    @Override
    public List<T> queryList(T entity) {
        return mapper.selectList(new QueryWrapper<>(entity));
    }

    @Override
    public IPage<T> queryPage(T entity, int pageNum, int pageSize) {
        IPage<T> page = new Page<>(pageNum, pageSize);
        return mapper.selectPage(page, new QueryWrapper<>(entity));
    }
}
