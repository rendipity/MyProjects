package com.mySpring.bean.factory;

import com.mySpring.bean.factory.Object.MySpring;
import com.mySpring.bean.factory.Object.MySpringReference;
import com.mySpring.bean.factory.config.BeanDefinition;
import com.mySpring.bean.factory.config.BeanReference;
import com.mySpring.bean.factory.support.DefaultListableBeanDefinition;
import org.junit.Test;

public class PropertyApplyTest {

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
    @Test
    public void testBeanReference(){
        DefaultListableBeanDefinition beanFactory = new DefaultListableBeanDefinition();
        BeanDefinition mySpringBeanDefinition = new BeanDefinition(MySpring.class);
        PropertyValues propertyValues =new PropertyValues();
        propertyValues.addProperty(new PropertyValue("mySpring",new BeanReference("mySpring")));
        BeanDefinition mySpringReferenceBeanDefinition = new BeanDefinition(MySpringReference.class,propertyValues);
        beanFactory.registerBeanDefinition("mySpring",mySpringBeanDefinition);
        beanFactory.registerBeanDefinition("mySpringReference",mySpringReferenceBeanDefinition);
        MySpringReference mySpringReference = (MySpringReference)beanFactory.getBean("mySpringReference");
        System.out.println(mySpringReference);
    }
}
