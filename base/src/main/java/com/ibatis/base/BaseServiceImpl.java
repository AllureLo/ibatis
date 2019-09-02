package com.ibatis.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author: ibatis
 * @Date: 19-8-16 下午6:48
 */
public abstract class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {

    /**
     * mapper类
     *
     * @return BaseMapper<T>
     */
    public abstract BaseMapper<T> mapper();

    @Override
    public void save(T entity) {
        if (entity.getId() == null) {
            this.insert(entity);
        } else {
            this.updateById(entity);
        }
    }

    @Override
    public void insert(T entity) {
        mapper().insert(entity);
    }

    @Override
    public void insertBatch(List<T> entities) {
        entities.forEach(this::insert);
    }

    @Override
    public void updateById(T entity) {
        mapper().updateById(entity);
    }

    @Override
    public void deleteById(Long id) {
        mapper().deleteByIdWithFill(id);
    }

    @Override
    public void deleteBatchByIds(List<Long> ids) {
        mapper().deleteBatchByIdsWithFill(ids);
    }

    @Override
    public T queryById(Long id) {
        return mapper().selectById(id);
    }

    @Override
    public T queryOne(T entity) {
        return mapper().selectOne(new QueryWrapper<>(entity));
    }

    @Override
    public int queryCount(T entity) {
        return mapper().selectCount(new QueryWrapper<>(entity));
    }

    @Override
    public List<T> queryList(T entity) {
        return mapper().selectList(new QueryWrapper<>(entity));
    }

    @Override
    public List<T> queryListByIds(List<Long> ids) {
        return mapper().selectBatchIds(ids);
    }

    @Override
    public IPage<T> queryPage(T entity, int pageNum, int pageSize) {
        IPage<T> page = new Page<>(pageNum, pageSize);
        return mapper().selectPage(page, new QueryWrapper<>(entity));
    }
}
