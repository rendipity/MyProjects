package com.mySpring;
import com.mySpring.bean.factory.support.DefaultListableBeanDefinition;
import com.mySpring.bean.factory.xml.XMLBeanDefinitionReader;
import com.mySpring.common.MyBeanFactoryPostProcessor;
import org.junit.Test;


public class BeanFactoryPostProcessorTest {

    @Test
    public void testBeanFactoryPostProcessor(){
        DefaultListableBeanDefinition factory = new DefaultListableBeanDefinition();
        XMLBeanDefinitionReader xmlBeanDefinitionReader =new XMLBeanDefinitionReader(factory);
        xmlBeanDefinitionReader.loadBeanDefinition("classpath:spring.xml");

        MyBeanFactoryPostProcessor myBeanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        myBeanFactoryPostProcessor.postProcessorBeanFactory(factory);

        System.out.println(factory.getBean("student"));
    }
}
