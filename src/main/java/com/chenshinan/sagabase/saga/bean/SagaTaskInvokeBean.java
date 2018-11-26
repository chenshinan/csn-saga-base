package com.chenshinan.sagabase.saga.bean;

import com.chenshinan.sagabase.saga.annotation.SagaTask;

import java.lang.reflect.Method;

/**
 * @author shinan.chen
 * @date 2018/8/29
 */
public class SagaTaskInvokeBean {
    final Method method;
    final Object object;
    final SagaTask sagaTask;

    public SagaTaskInvokeBean(Method method, Object object, SagaTask sagaTask) {
        this.method = method;
        this.object = object;
        this.sagaTask = sagaTask;
    }
}
