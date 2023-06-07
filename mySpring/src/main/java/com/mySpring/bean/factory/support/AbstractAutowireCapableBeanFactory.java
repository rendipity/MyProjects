package com.mySpring.bean.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.mySpring.bean.BeansException;
import com.mySpring.bean.factory.PropertyValue;
import com.mySpring.bean.factory.config.BeanDefinition;


public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    @Override
    protected Object createBean(String beanName,BeanDefinition beanDefinition) {
        return doCreateBean(beanName,beanDefinition);
    }
    public Object doCreateBean(String beanName,BeanDefinition beanDefinition){
         Object bean = null;
         try {
             bean = createBeanInstance(beanName,beanDefinition);
             applyAttributes(bean,beanDefinition,beanName);
         }catch (Exception e){
             throw new BeansException("failed create bean:"+beanName);
         }

        addSingleton(beanName,beanDefinition);
        return bean;
    }
    private Object createBeanInstance(String beanName,BeanDefinition beanDefinition) throws BeansException{
        Class beanClass =beanDefinition.getBeanClass();
        try {
            return  beanClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeansException("failed create bean:"+beanName);
        }
    }
    // 填充属性
    private Object applyAttributes(Object bean,BeanDefinition beanDefinition,String beanName){
        try {
            PropertyValue[] propertyValues = beanDefinition.getPropertyValues().getPropertyValues();
            for(PropertyValue propertyValue:propertyValues){
                //通过反射设置属性
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                BeanUtil.setFieldValue(bean, name, value);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BeansException("failed create bean:"+beanName);
        }
        return bean;
    }
}
