package com.chenshinan.sagabase.saga.bean;

import com.chenshinan.sagabase.saga.annotation.Saga;

import java.lang.reflect.Method;

/**
 * @author shinan.chen
 * @date 2018/8/29
 */
public class SagaTaskInvokeBean {
    final Method method;
    final Object object;
    final Saga saga;

    public SagaTaskInvokeBean(Method method, Object object, Saga saga) {
        this.method = method;
        this.object = object;
        this.saga = saga;
    }


}
