package com.mySpring.bean.factory;

import com.mySpring.bean.factory.Object.MySpring;
import com.mySpring.bean.factory.config.BeanDefinition;
import com.mySpring.bean.factory.support.DefaultListableBeanDefinition;
import org.junit.Test;

public class BeanDefinitionTest {

    @Test
    public void testBeanDefinition(){
        DefaultListableBeanDefinition beanFactory = new DefaultListableBeanDefinition();
        BeanDefinition mySpringBeanDefinition = new BeanDefinition(MySpring.class);
        beanFactory.registerBeanDefinition("mySpring",mySpringBeanDefinition);
        MySpring mySpring = (MySpring)beanFactory.getBean("mySpring");
        mySpring.hello();
    }

}
