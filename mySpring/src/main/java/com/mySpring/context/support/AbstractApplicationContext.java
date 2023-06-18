package com.mySpring.context.support;


import com.mySpring.bean.factory.ConfigurableListableBeanFactory;
import com.mySpring.bean.factory.config.BeanFactoryPostProcessor;
import com.mySpring.bean.factory.config.BeanPostProcessor;
import com.mySpring.context.ConfigurableApplicationContext;
import com.mySpring.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() {
        // 初始化beanFactory 并加载 BeanDefinitions
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        // 执行所有的BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);
        // 注册所有的BeanPostProcessor
        registerBeanPostProcessors(beanFactory);
        // 实例化所有的单例bean
        beanFactory.preInstantiateSingletons();
    }

    abstract void refreshBeanFactory();

    abstract ConfigurableListableBeanFactory getBeanFactory();

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = getBeansOfType(BeanFactoryPostProcessor.class);
        for(BeanFactoryPostProcessor factoryPostProcessor:beanFactoryPostProcessorMap.values()){
            factoryPostProcessor.postProcessorBeanFactory(beanFactory);
        }
    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactor){
        Map<String, BeanPostProcessor> beanPostProcessorMap = getBeansOfType(BeanPostProcessor.class);
        for(BeanPostProcessor beanPostProcessor:beanPostProcessorMap.values()){
            beanFactor.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public Object getBean(String beanName) {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}
