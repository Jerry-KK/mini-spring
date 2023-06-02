package com.minis.beans.factory;

import com.minis.exceptions.BeansException;


/**
 * @author lethe
 * @date 2023/5/29 21:42
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    Boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class getType(String name);
}
