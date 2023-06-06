package com.mySpring.bean.factory.support;

import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{

    Map<String,Object> singletonMap =new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonMap.get(beanName);
    }


    public Object setSingleton(String beanName,Object bean) {
        return singletonMap.put(beanName,bean);
    }
}
