package com.mySpring.bean.factory.support;

import com.mySpring.bean.BeansException;
import com.mySpring.bean.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

public class DefaultListableBeanDefinition extends AbstractAutowireCapableBeanFactory  implements BeanDefinitionRegistry  {

    private Map<String, BeanDefinition> beanDefinitionMap=new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName,BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition ==null)
            throw new BeansException("no bean named '"+beanName);
        return beanDefinition;
    }
}