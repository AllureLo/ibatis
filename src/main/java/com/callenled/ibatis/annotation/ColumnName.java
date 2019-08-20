package com.callenled.ibatis.annotation;

import java.lang.annotation.*;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午5:21
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnName {
    String value();
}
