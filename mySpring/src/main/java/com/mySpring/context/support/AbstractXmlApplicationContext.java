package com.mySpring.context.support;

import com.mySpring.bean.factory.support.DefaultListableBeanDefinition;
import com.mySpring.bean.factory.xml.XMLBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanDefinition beanFactory) {
        // 创建ResourceReader
        XMLBeanDefinitionReader xmlReader = new XMLBeanDefinitionReader(beanFactory,this);
        // 遍历所有定义了BeanDefinition的文件生成BeanDefinition
        String[] configLocations = getConfigLocations();
        if (configLocations != null){
            xmlReader.loadBeanDefinition(configLocations);
        }
    }

    abstract String[] getConfigLocations();
}
