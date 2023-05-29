package com.minis.context;

import com.minis.beans.BeanDefinition;
import com.minis.beans.*;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;
import com.minis.core.XmlBeanDefinitionReader;
import com.minis.exceptions.BeansException;

/**
 * @author lethe
 * @date 2023/5/29 22:06
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    BeanFactory beanFactory;

    //context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        BeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    //context再对外提供一个getBean，底下就是调用的BeanFactory对应的方法
    @Override
    public Object getBean(String beanName) throws BeansException {
        return beanFactory.getBean(beanName);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        beanFactory.registerBeanDefinition(beanDefinition);
    }
}
