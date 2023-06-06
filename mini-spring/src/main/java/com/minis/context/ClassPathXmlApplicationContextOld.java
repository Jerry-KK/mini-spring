package com.minis.context;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.annotation.AbstractAutowireCapableBeanFactory;
import com.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.xml.ClassPathXmlResource;
import com.minis.beans.factory.xml.Resource;
import com.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.events.ApplicationEvent;
import com.minis.events.ApplicationEventPublisher;
import com.minis.events.ApplicationListener;
import com.minis.exceptions.BeansException;

/**
 * @author lethe
 * @date 2023/5/29 22:06
 */
public class ClassPathXmlApplicationContextOld implements BeanFactory, ApplicationEventPublisher {

    AbstractAutowireCapableBeanFactory beanFactory;

    //context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
    public ClassPathXmlApplicationContextOld(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContextOld(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        AbstractAutowireCapableBeanFactory beanFactory = new AbstractAutowireCapableBeanFactory() {
            //todo
            @Override
            public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
                return null;
            }

            @Override
            public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
                return null;
            }
        };
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

    private void registerBeanPostProcessors(AbstractAutowireCapableBeanFactory beanFactory) {
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

    @Override
    public void addApplicationListener(ApplicationListener listener) {

    }
}
