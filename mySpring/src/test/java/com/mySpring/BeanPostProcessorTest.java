package com.mySpring;

import com.mySpring.bean.factory.support.DefaultListableBeanDefinition;
import com.mySpring.bean.factory.xml.XMLBeanDefinitionReader;
import com.mySpring.common.MyBeanPostProcessor;
import org.junit.Test;

public class BeanPostProcessorTest {

    @Test
    public void testBeanPostProcessor(){
        DefaultListableBeanDefinition factory = new DefaultListableBeanDefinition();
        XMLBeanDefinitionReader xmlBeanDefinitionReader =new XMLBeanDefinitionReader(factory);
        xmlBeanDefinitionReader.loadBeanDefinition("classpath:spring.xml");

        MyBeanPostProcessor myBeanPostProcessor =new MyBeanPostProcessor();
        factory.addBeanPostProcessor(myBeanPostProcessor);

        System.out.println(factory.getBean("student"));
    }
}
