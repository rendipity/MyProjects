package com.mySpring.context;

import com.mySpring.bean.factory.HierarchicalBeanFactory;
import com.mySpring.bean.factory.ListableBeanFactory;
import com.mySpring.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {

}
