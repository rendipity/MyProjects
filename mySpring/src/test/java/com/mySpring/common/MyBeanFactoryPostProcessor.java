package com.mySpring.common;

import com.mySpring.bean.BeansException;
import com.mySpring.bean.factory.ConfigurableListableBeanFactory;
import com.mySpring.bean.factory.PropertyValue;
import com.mySpring.bean.factory.PropertyValues;
import com.mySpring.bean.factory.config.BeanDefinition;
import com.mySpring.bean.factory.config.BeanFactoryPostProcessor;

/**
 * 修改修改studentBean的name
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessorBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition studentDefinition = beanFactory.getBeanDefinition("student");
        PropertyValues propertyValues = studentDefinition.getPropertyValues();
        propertyValues.addProperty(new PropertyValue("name","nameChangedByBeanFactoryPostProcessor"));
    }
}
