package com.callenled.ibatis;

import java.io.Serializable;

/**
 * @Author: callenled
 * @Date: 19-8-16 上午11:19
 */
public class Property implements Serializable {

    private static final long serialVersionUID = 5892628927801063087L;
    private String name;

    private Object value;

    public Property(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", name=" + name +
                ", value=" + value +
                "]";
    }
}
