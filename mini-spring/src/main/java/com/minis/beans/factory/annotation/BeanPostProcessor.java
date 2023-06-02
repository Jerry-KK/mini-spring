package com.minis.beans.factory.annotation;

import com.minis.exceptions.BeansException;

/**
 * @author lethe
 * @date 2023/6/2 12:18
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
