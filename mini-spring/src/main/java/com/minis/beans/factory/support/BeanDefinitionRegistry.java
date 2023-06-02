package com.minis.beans.factory.support;

import com.minis.beans.factory.config.BeanDefinition;

/**
 * @author lethe
 * @date 2023/5/29 23:52
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);

}
