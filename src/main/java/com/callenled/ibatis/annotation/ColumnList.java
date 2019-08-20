package com.callenled.ibatis.annotation;

import java.lang.annotation.*;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午5:09
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnList {
    String value();
}
