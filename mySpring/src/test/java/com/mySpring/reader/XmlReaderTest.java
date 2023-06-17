package com.mySpring.reader;

import com.mySpring.bean.factory.support.DefaultListableBeanDefinition;
import com.mySpring.bean.factory.xml.XMLBeanDefinitionReader;
import com.mySpring.reader.Object.Student;
import org.junit.Test;

public class XmlReaderTest {

    @Test
    public void xmlReaderTest(){
        DefaultListableBeanDefinition factory = new DefaultListableBeanDefinition();
        XMLBeanDefinitionReader xmlBeanDefinitionReader =new XMLBeanDefinitionReader(factory);
        xmlBeanDefinitionReader.loadBeanDefinition("classpath:spring.xml");
        Student student = (Student)factory.getBean("student");
        System.out.println(student.getCar());
        System.out.println(student);
    }
}
