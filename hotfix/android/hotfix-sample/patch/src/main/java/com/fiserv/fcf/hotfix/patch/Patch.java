package com.fiserv.fcf.hotfix.patch;

public interface Patch {

    public Object dispatchMethod(Object host, String methodSign, Object[] params);

}
