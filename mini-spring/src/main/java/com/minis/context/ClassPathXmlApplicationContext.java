package com.minis.context;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.annotation.AutowireCapableBeanFactory;
import com.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.xml.ClassPathXmlResource;
import com.minis.beans.factory.xml.Resource;
import com.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.events.ApplicationEvent;
import com.minis.events.ApplicationEventPublisher;
import com.minis.exceptions.BeansException;

/**
 * @author lethe
 * @date 2023/5/29 22:06
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    AutowireCapableBeanFactory beanFactory;

    //context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            try {
                refresh();
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
        // 省略方法实现
    }

    //todo BeanFactoryPostProcessor 待实现
    /*public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return this.beanFactoryPostProcessors;
    }

    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }*/

    public void refresh() throws BeansException, IllegalStateException {
        // Register bean processors that intercept bean creation.
        registerBeanPostProcessors(this.beanFactory);
        // Initialize other special beans in specific context subclasses.
        onRefresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }
    private void onRefresh() {
        this.beanFactory.refresh();
    }

    //context再对外提供一个getBean，底下就是调用的BeanFactory对应的方法
    @Override
    public Object getBean(String beanName) throws BeansException {
        return beanFactory.getBean(beanName);
    }

    @Override
    public Boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class getType(String name) {
        return null;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
