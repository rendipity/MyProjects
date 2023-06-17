package com.mySpring.bean.factory.support;

import com.mySpring.bean.BeansException;
import com.mySpring.bean.factory.PropertyValue;
import com.mySpring.bean.factory.config.BeanDefinition;
import com.mySpring.bean.factory.config.BeanPostProcessor;
import com.mySpring.bean.factory.config.BeanReference;

import java.lang.reflect.Field;
import java.util.List;


public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName,BeanDefinition beanDefinition) {
        return doCreateBean(beanName,beanDefinition);
    }
    public Object doCreateBean(String beanName,BeanDefinition beanDefinition){
         Object bean = null;
         try {
             // bean实例化
             bean = createBeanInstance(beanName,beanDefinition);
             // 依赖注入
             applyAttributes(bean,beanDefinition,beanName);
             // 初始化
             bean = initializeBean(beanName, bean,beanDefinition);
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
    private void applyAttributes(Object bean,BeanDefinition beanDefinition,String beanName){
        try {
            PropertyValue[] propertyValues = beanDefinition.getPropertyValues().getPropertyValues();
            for(PropertyValue propertyValue:propertyValues){
                //通过反射设置属性
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                if (value instanceof BeanReference)
                    value = getBean(((BeanReference)value).getBeanName());
                Field variable = bean.getClass().getDeclaredField(name);
                variable.setAccessible(true);
                variable.set(bean,value);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BeansException("failed create bean:"+beanName);
        }
    }
    private Object initializeBean( String beanName,Object bean, BeanDefinition beanDefinition){
        // 执行所有beanPostProcessor的beforeInitiation
        Object wrappedBean = applyBeanProcessorBeforeInitiation(beanName,bean);
        // 执行初始化方法
        // todo 初始化后续再做
        doInitialization(beanName,bean,beanDefinition);
        // 执行所有beanPostProcessor的AfterInitiation
        wrappedBean = applyBeanProcessorAfterInitiation(beanName,bean);
        return wrappedBean;
    }

    private Object applyBeanProcessorBeforeInitiation(String beanName, Object bean){
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        Object result = bean;
        for(BeanPostProcessor postProcessor:beanPostProcessors){
          Object current = postProcessor.postProcessorBeforeInitialization(beanName,result);
          if (current == null)
              return result;
          result = current;
        }
        return result;
    }

    private Object applyBeanProcessorAfterInitiation(String beanName, Object bean){
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        Object result = bean;
        for(BeanPostProcessor postProcessor:beanPostProcessors){
            Object current = postProcessor.postProcessorAfterInitialization(beanName,result);
            if (current == null)
                return result;
            result = current;
        }
        return result;
    }
    // todo 初始化后续再做
    private void doInitialization(String beanName, Object bean, BeanDefinition beanDefinition){
        System.out.println("执行bean[" + beanName + "]的初始化方法");
    }
}
