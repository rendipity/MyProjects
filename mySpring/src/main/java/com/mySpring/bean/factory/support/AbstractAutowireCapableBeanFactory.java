package com.mySpring.bean.factory.support;

import com.mySpring.bean.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    @Override
    protected Object createBean(String beanName,BeanDefinition beanDefinition) {
        return doCreateBean(beanName,beanDefinition);
    }
    public Object doCreateBean(String beanName,BeanDefinition beanDefinition){
         Class beanClass =beanDefinition.getBeanClass();
         Object bean = null;
        try {
            bean = beanClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        addSingleton(beanName,beanDefinition);
        return bean;
    }
}
