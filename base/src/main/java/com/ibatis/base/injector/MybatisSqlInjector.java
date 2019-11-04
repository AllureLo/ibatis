package com.ibatis.base.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.additional.LogicDeleteByIdWithFill;
import com.ibatis.base.method.LogicDeleteBatchByIdsWithFill;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: ibatis
 * @Date: 19-8-21 下午5:54
 */
@Component
public class MybatisSqlInjector extends DefaultSqlInjector {

    /**
     * 如果只需增加方法，保留MP自带方法
     * 可以super.getMethodList() 再add
     *
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        //逻辑删除方法
        methodList.add(new LogicDeleteByIdWithFill());
        //批量逻辑删除方法
        methodList.add(new LogicDeleteBatchByIdsWithFill());
        return methodList;
    }
}
