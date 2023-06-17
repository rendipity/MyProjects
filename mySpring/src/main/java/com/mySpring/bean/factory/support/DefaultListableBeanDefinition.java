package com.mySpring.bean.factory.support;

import com.mySpring.bean.BeansException;
import com.mySpring.bean.factory.ConfigurableListableBeanFactory;
import com.mySpring.bean.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

public class DefaultListableBeanDefinition extends AbstractAutowireCapableBeanFactory  implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap=new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName,BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition ==null)
            throw new BeansException("no bean named '"+beanName);
        return beanDefinition;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }
}
