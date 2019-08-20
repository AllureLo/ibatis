package com.callenled.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: callenled
 * @Date: 19-8-19 下午5:09
 */
@Component
@Intercepts({@Signature(type = Executor.class,method = "update",args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class, Object.class, RowBounds.class,ResultHandler.class})})
public class MybatisInterceptor implements Interceptor {

    private Map<String, Map<String, Object>> cache = new HashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String sqlId = mappedStatement.getId();

        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);

        boolean flag = mappedStatement.getResultMaps() != null && !mappedStatement.getResultMaps().isEmpty();
        if (SqlCommandType.SELECT == mappedStatement.getSqlCommandType() && flag ) {

        }
        return invocation.proceed();
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
