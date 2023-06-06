package com.mySpring.bean.factory.support;

import com.mySpring.bean.factory.BeanFactory;
import com.mySpring.bean.factory.config.BeanDefinition;

/**
 * 实现getBean
 * 定义getBeanDefinition、createBean
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String beanName) {
        Object bean = getSingleton(beanName);
        if (bean != null){
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName,beanDefinition);
    }
    // protected 才能被子类继承使用
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    protected abstract Object createBean(String beanName,BeanDefinition beanDefinition);
}
