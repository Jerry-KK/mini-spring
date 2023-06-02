package com.minis.beans.factory.support;

import com.minis.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lethe
 * @date 2023/5/29 22:38
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    //容器中存放所有bean的名称列表
    protected List<String> beanNames = new ArrayList<>();
    //容器中存放所有bean实例的map
    protected Map<String, Object> singletons = new ConcurrentHashMap<>(256);

    //存放的就是早期的毛胚实例
    protected Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(256);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (singletons) {
            this.singletons.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }

    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return this.beanNames.toArray(new String[0]);
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletons) {
            this.beanNames.remove(beanName);
            this.singletons.remove(beanName);
        }
    }
}
