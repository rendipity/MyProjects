package com.mySpring.bean.factory.config;

import com.mySpring.bean.factory.HierarchicalBeanFactory;
import com.mySpring.bean.factory.support.SingletonBeanRegistry;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
