package com.callenled.service.impl;

import com.callenled.ibatis.BaseDao;
import com.callenled.ibatis.BaseModel;
import com.callenled.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午6:48
 */
public abstract class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {

    @Autowired
    private BaseDao<T> baseDao;

    /**
     * model
     *
     * @return
     */
    public abstract Class<T> getClazz();

    @Override
    public void save(T object) {
        baseDao.save(object, getClazz());
    }

    @Override
    public void insert(T object) {
        baseDao.add(object, getClazz());
    }

    @Override
    public void update(T object) {
        baseDao.update(object, getClazz());
    }

    @Override
    public void insertBatch(List<T> list) {
        baseDao.addList(list, getClazz());
    }

    @Override
    public T queryById(Long id) {
        return baseDao.quickQueryOneById(id, getClazz());
    }

    @Override
    public T queryOneByName(String name, Object value) {
        return baseDao.quickQueryOne(name, value, getClazz());
    }

    @Override
    public void deleteById(T object) {
        baseDao.deleteById(object, getClazz());
    }

    @Override
    public void deleteById(Long id) {
        baseDao.deleteById(id, getClazz());
    }

    @Override
    public void deleteByName(String name, Object value) {
        baseDao.delete(name, value, getClazz());
    }

    @Override
    public void deleteById(Long id, boolean isDelete) {
        if (isDelete) {
            baseDao.quickDeleteById(id, getClazz());
        } else {
            baseDao.deleteById(id, getClazz());
        }
    }

    @Override
    public void deleteByName(String name, Object value,  boolean isDelete) {
        if (isDelete) {
            baseDao.quickDelete(name, value, getClazz());
        } else {
            baseDao.delete(name, value, getClazz());
        }
    }

    @Override
    public int queryCount(T object) {
        return baseDao.queryCount(object, getClazz());
    }

    @Override
    public List<T> queryList(T object, Integer start, Integer limit) {
        return baseDao.query(object, start, limit, getClazz());
    }

    @Override
    public int queryUseLikeCount(T object, List<String> likeProperties) {
        return baseDao.queryCountUsingLike(object, likeProperties, getClazz());
    }

    @Override
    public List<T> queryUseLikeList(T object, List<String> likeProperties, Integer start, Integer limit) {
        return baseDao.queryUsingLike(object, likeProperties, start, limit, getClazz());
    }

    @Override
    public int queryCustomizeCount(T object, List<String> userPara) {
        return baseDao.queryCountCustomize(object, userPara, getClazz());
    }

    @Override
    public List<T> queryCustomizeList(T object, List<String> userPara, Integer start, Integer limit) {
        return baseDao.queryCustomize(object, userPara, start, limit, getClazz());
    }

    @Override
    public int queryCount(T object, List<String> likeProperties, List<String> userPara) {
        return baseDao.queryCount(object, likeProperties, userPara, getClazz());
    }

    @Override
    public List<T> queryList(T object, List<String> likeProperties, List<String> userPara, Integer start, Integer limit) {
        return baseDao.query(object, likeProperties, userPara, start, limit, getClazz());
    }
}
