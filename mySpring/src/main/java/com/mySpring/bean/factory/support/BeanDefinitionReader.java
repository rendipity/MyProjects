package com.mySpring.bean.factory.support;

import com.mySpring.core.io.Resource;
import com.mySpring.core.io.ResourceLoader;

public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinition(String location);

    void loadBeanDefinition(String[] locations);

    void loadBeanDefinition(Resource resource);
}
