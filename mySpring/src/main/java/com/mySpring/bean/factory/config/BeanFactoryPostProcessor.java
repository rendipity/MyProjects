package com.mySpring.bean.factory.config;

import com.mySpring.bean.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {
    /**
     * 在BeanDefinition执行完成之后，Bean实例化之前执行
     */
    void postProcessorBeanFactory(ConfigurableListableBeanFactory beanFactory);
}
