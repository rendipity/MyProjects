package com.mySpring.bean.factory;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {

    <T> Map<String,T> getBeansOfType(Class<T> type);

    String[] getBeanDefinitionNames();
}
