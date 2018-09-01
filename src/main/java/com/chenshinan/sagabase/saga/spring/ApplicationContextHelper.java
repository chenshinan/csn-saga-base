package com.chenshinan.sagabase.saga.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author shinan.chen
 * @date 2018/8/29
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

    public static ApplicationContext getContext(){
        return applicationContext;
    }
}
