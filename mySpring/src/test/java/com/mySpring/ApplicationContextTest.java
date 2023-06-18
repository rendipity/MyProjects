package com.mySpring;

import com.mySpring.context.support.ClassPathXmlApplicationContext;
import com.mySpring.reader.Object.Student;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTest {

    @Test
    public void testApplicationContext(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Student student = (Student)applicationContext.getBean("student");
        System.out.println(student);
        Assert.assertEquals("nameChangedByBeanFactoryPostProcessor",student.getName());
        Assert.assertEquals("Benz",student.getCar().getBrand());
    }
}
