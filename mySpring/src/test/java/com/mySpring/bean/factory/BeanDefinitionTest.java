package com.mySpring.bean.factory;

import com.mySpring.bean.factory.config.BeanDefinition;
import com.mySpring.bean.factory.support.DefaultListableBeanDefinition;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanDefinitionTest {

    @Test
    public void testBeanDefinition(){
        DefaultListableBeanDefinition beanFactory = new DefaultListableBeanDefinition();
        BeanDefinition mySpringBeanDefinition = new BeanDefinition(MySpring.class);
        beanFactory.registerBeanDefinition("mySpring",mySpringBeanDefinition);
        MySpring mySpring = (MySpring)beanFactory.getBean("mySpring");
        mySpring.hello();
    }
    @Test
    public void testPropertyValues(){
        DefaultListableBeanDefinition beanFactory = new DefaultListableBeanDefinition();
        PropertyValues propertyValues =new PropertyValues();
        propertyValues.addProperty(new PropertyValue("msg","applyProperty 0.0.1"));
        BeanDefinition mySpringBeanDefinition = new BeanDefinition(MySpring.class,propertyValues);
        beanFactory.registerBeanDefinition("mySpring",mySpringBeanDefinition);
        MySpring mySpring = (MySpring)beanFactory.getBean("mySpring");
        System.out.println(mySpring);
    }
}
