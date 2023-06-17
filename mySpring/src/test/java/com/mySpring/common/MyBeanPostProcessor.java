package com.mySpring.common;

import com.mySpring.bean.factory.config.BeanPostProcessor;
import com.mySpring.reader.Object.Car;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessorBeforeInstantiation(String beanName, Object bean) {
        return null;
    }

    @Override
    public Object postProcessorAfterInstantiation(String beanName, Object bean) {
        return null;
    }

    @Override
    public Object postProcessorProperties(String beanName, Object bean) {
        return null;
    }

    @Override
    public Object postProcessorBeforeInitialization(String beanName, Object bean) {
       if ("car".equals(beanName)){
           System.out.println(beanName+"'s brand changed from "+((Car)bean).getBrand()+" to Benz");
           ((Car)bean).setBrand("Benz");
       }
       return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(String beanName, Object bean) {
        System.out.println(beanName+"---postProcessorAfterInitialization 执行了");
        return bean;
    }
}
