package com.mySpring.bean.factory.config;

public interface BeanPostProcessor {

    // 实例化前执行的方法 --- 暂不支持
    Object postProcessorBeforeInstantiation(String beanName, Object bean);
    // 实例化后执行的方法 --- 暂不支持
    Object postProcessorAfterInstantiation(String beanName, Object bean);
    // 依赖注入时执行的方法 --- 暂不支持
    Object postProcessorProperties(String beanName, Object bean);
    // 初始化前执行的方法
    Object postProcessorBeforeInitialization(String beanName, Object bean);
    // 初始化后执行的方法
    Object postProcessorAfterInitialization(String beanName, Object bean);
}
