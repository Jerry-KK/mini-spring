package com.minis.beans.factory;

import com.minis.beans.factory.annotation.BeanPostProcessor;
import com.minis.beans.factory.config.SingletonBeanRegistry;

/**
 * @author lethe
 * @date 2023/6/2 14:11
 */

/**
 * 维护 Bean 之间的依赖关系以及支持 Bean 处理器
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
    int getBeanPostProcessorCount();
    void registerDependentBean(String beanName, String dependentBeanName);
    String[] getDependentBeans(String beanName);
    String[] getDependenciesForBean(String beanName);
}
