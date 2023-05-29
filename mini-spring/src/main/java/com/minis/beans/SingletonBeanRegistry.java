package com.minis.beans;

/**
 * @author lethe
 * @date 2023/5/29 22:33
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);
    Object getSingleton(String beanName);
    boolean containsSingleton(String beanName);
    String[] getSingletonNames();

}
