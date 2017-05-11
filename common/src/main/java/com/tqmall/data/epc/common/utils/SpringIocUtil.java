package com.tqmall.data.epc.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringIocUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static Object getBean(String id) {
        return applicationContext.getBean(id);
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
