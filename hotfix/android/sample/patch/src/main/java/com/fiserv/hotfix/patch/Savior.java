package com.fiserv.hotfix.patch;

public interface Savior {

    public Object dispatchMethod(Object host, String signature, Object[] params);

}