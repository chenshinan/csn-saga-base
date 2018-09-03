package com.chenshinan.sagabase.saga.spring;

import com.chenshinan.sagabase.saga.SagaMonitor;
import com.chenshinan.sagabase.saga.annotation.Saga;
import com.chenshinan.sagabase.saga.bean.SagaTaskInvokeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author shinan.chen
 * @date 2018/8/29
 */
@Component
public class SagaProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SagaProcessor.class);

    @Autowired
    private ApplicationContextHelper applicationContextHelper;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        Class clazz = bean.getClass();
//        Method[] methods = clazz.getDeclaredMethods();
//        for (Method method : methods) {
//            if (method.isAnnotationPresent(Saga.class)) {
//                Saga saga = method.getAnnotation(Saga.class);
//                System.out.println("sage:"+saga);
//            }
//        }

        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        for (Method method : methods) {
            Saga saga = AnnotationUtils.getAnnotation(method, Saga.class);
            if (saga != null) {
                LOGGER.info("saga annotation:{}", saga);
                System.out.println(method.getDeclaringClass());
                Object object = applicationContextHelper.getContext().getBean(method.getDeclaringClass());
                SagaMonitor.invokeBeanMap.put(saga.code(), new SagaTaskInvokeBean(method, object, saga));
            }
        }
        return bean;
    }
}
