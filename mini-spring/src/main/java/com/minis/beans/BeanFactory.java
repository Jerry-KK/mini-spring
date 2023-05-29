package com.minis.beans;

import com.minis.exceptions.BeansException;

/**
 * @author lethe
 * @date 2023/5/29 21:42
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;
    void registerBeanDefinition(BeanDefinition beanDefinition);
}
