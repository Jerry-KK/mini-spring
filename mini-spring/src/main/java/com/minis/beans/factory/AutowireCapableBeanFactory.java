package com.minis.beans.factory;

import com.minis.exceptions.BeansException;

/**
 * @author lethe
 * @date 2023/6/2 14:25
 */

/**
 * 自动装配Bean
 */
public interface AutowireCapableBeanFactory extends BeanFactory{

    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
