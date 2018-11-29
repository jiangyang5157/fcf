package com.wangxiandeng.savior;

/**
 * Created by xingzhu on 16/9/20.
 */

public interface Savior {
    public Object dispatchMethod(Object host, String methodSign, Object[] params);
}
