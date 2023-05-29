package com.minis.beans;

import com.minis.exceptions.BeansException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lethe
 * @date 2023/5/29 21:54
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory,BeanDefinitionRegistry{

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private List<String> beanDefinitionNames = new ArrayList<>();


    public SimpleBeanFactory() {

    }

    //getBean,容器的核心方法
    @Override
    public Object getBean(String beanName) throws BeansException {
        //todo
        return null;
    }

    @Override
    public Boolean containsBean(String name) {
        return containsSingleton(name);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        if(!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {

            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }


}
