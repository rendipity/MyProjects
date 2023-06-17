package com.mySpring.bean.factory.support;

import com.mySpring.bean.factory.BeanFactory;
import com.mySpring.bean.factory.config.BeanDefinition;
import com.mySpring.bean.factory.config.BeanPostProcessor;
import com.mySpring.bean.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现getBean
 * 定义getBeanDefinition、createBean
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, ConfigurableBeanFactory {

    List<BeanPostProcessor> beanPostProcessorList =new ArrayList<>();

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

    /**
     * 注册BeanPostProcessor实例
     * @param beanPostProcessor
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessorList.remove(beanPostProcessor);
        beanPostProcessorList.add(beanPostProcessor);
    }

    /**
     * 获得所有注册的beanPostProcessor实例
     * @return
     */
    public List<BeanPostProcessor> getBeanPostProcessors(){
        return beanPostProcessorList;
    }

}
