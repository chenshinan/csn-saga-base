package com.chenshinan.sagabase.saga.bean;

import com.chenshinan.sagabase.saga.annotation.Saga;

import java.lang.reflect.Method;

/**
 * @author shinan.chen
 * @date 2018/8/29
 */
public class SagaInvokeBean {
    private Method method;
    private Object object;
    private Saga saga;

    public SagaInvokeBean(Method method, Object object, Saga saga) {
        this.method = method;
        this.object = object;
        this.saga = saga;
    }
}
