package com.minis.context;

import com.minis.beans.factory.ConfigurableListableBeanFactory;
import com.minis.beans.factory.DefaultListableBeanFactory;
import com.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.annotation.BeanFactoryPostProcessor;
import com.minis.beans.factory.xml.ClassPathXmlResource;
import com.minis.beans.factory.xml.Resource;
import com.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.events.ApplicationEvent;
import com.minis.events.ApplicationListener;
import com.minis.events.ContextRefreshEvent;
import com.minis.events.SimpleApplicationEventPublisher;
import com.minis.exceptions.BeansException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lethe
 * @date 2023/6/2 15:47
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    DefaultListableBeanFactory beanFactory;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();


    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
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
    }

    @Override
    void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    void initApplicationEventPublisher() {
        SimpleApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed..."));
    }


    //todo


}
