package com.chenshinan.sagabase.saga.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shinan.chen
 * @since 2018/11/26
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SagaTask {
    String code();

    String sagaCode();

    String description() default "";

    int seq() default 1;
}
