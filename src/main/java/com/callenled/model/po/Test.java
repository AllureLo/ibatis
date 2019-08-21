package com.callenled.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.callenled.base.BaseModel;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午4:03
 */
@TableName(value = "test")
public class Test extends BaseModel {

    private static final long serialVersionUID = 2710501552813073973L;

    /**
     * 参数
     */
    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
