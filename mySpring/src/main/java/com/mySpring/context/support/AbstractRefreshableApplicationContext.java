package com.mySpring.context.support;

import com.mySpring.bean.factory.ConfigurableListableBeanFactory;
import com.mySpring.bean.factory.support.DefaultListableBeanDefinition;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private DefaultListableBeanDefinition beanFactory;

    @Override
    void refreshBeanFactory() {
        beanFactory = new DefaultListableBeanDefinition();
        loadBeanDefinitions(beanFactory);
    }

    @Override
    ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }
    protected abstract void loadBeanDefinitions(DefaultListableBeanDefinition beanFactory);

}
