package com.chenshinan.sagabase.saga.spring;

import com.chenshinan.sagabase.saga.annotation.Saga;
import com.chenshinan.sagabase.saga.annotation.SagaTask;
import com.chenshinan.sagabase.saga.bean.SagaTaskInvokeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static com.chenshinan.sagabase.saga.SagaMonitor.invokeBeanMap;

/**
 * @author shinan.chen
 * @date 2018/8/29
 */
@Component
public class SagaProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SagaProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        for (Method method : methods) {
            SagaTask sagaTask = AnnotationUtils.getAnnotation(method, SagaTask.class);
            if (sagaTask != null) {
                LOGGER.info("sagaTask annotation:{}", sagaTask);
                String key = sagaTask.code()+":"+sagaTask.sagaCode();
                invokeBeanMap.put(key, new SagaTaskInvokeBean(method, bean, sagaTask));
            }
        }
        return bean;
    }
}
