package com.mySpring.bean.factory;

import com.mySpring.bean.BeansException;
import com.mySpring.bean.factory.config.AutowireCapableBeanFactory;
import com.mySpring.bean.factory.config.BeanDefinition;
import com.mySpring.bean.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ConfigurableBeanFactory, ListableBeanFactory, AutowireCapableBeanFactory {
    /**
     *
     * @param beanName
     * @return
     * @throws BeansException 找不到bean就抛出异常
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;


    void preInstantiateSingletons() throws BeansException;
}
