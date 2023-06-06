package com.mySpring.bean.factory.support;

import com.mySpring.bean.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

public class DefaultListableBeanDefinition implements BeanDefinitionRegistry {

    Map<String, BeanDefinition> beansMap=new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName,BeanDefinition beanDefinition) {
        beansMap.put(beanName,beanDefinition);
    }
}
