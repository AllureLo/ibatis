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
    public T selectById(Long id) {
        return mapper().selectById(id);
    }

    @Override
    public T selectOne(T entity) {
        return mapper().selectOne(new QueryWrapper<>(entity));
    }

    @Override
    public int selectCount(T entity) {
        return mapper().selectCount(new QueryWrapper<>(entity));
    }

    @Override
    public List<T> selectList(T entity, int pageNum, int pageSize) {
        return mapper().selectList(new QueryWrapper<>(entity).last("limit " + (pageNum - 1) * pageSize + "," + pageSize));
    }

    @Override
    public List<T> selectByIds(List<Long> ids) {
        return mapper().selectBatchIds(ids);
    }

    @Override
    public IPage<T> selectForPage(T entity, int pageNum, int pageSize) {
        IPage<T> page = new Page<>(pageNum, pageSize);

        int total = selectCount(entity);
        page.setTotal(total);

        if (total > 0) {
            List<T> records = this.selectList(entity, pageNum, pageSize);
            page.setRecords(records);
        }

        return page;
    }
}
