package com.minis.context;

import com.minis.beans.factory.ConfigurableBeanFactory;
import com.minis.beans.factory.ConfigurableListableBeanFactory;
import com.minis.beans.factory.ListableBeanFactory;
import com.minis.beans.factory.annotation.BeanFactoryPostProcessor;
import com.minis.core.env.Environment;
import com.minis.core.env.EnvironmentCapable;
import com.minis.events.ApplicationEventPublisher;
import com.minis.exceptions.BeansException;

/**
 * @author lethe
 * @date 2023/6/2 15:27
 */
public interface ApplicationContext
        extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {
    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws
            IllegalStateException;

    void setEnvironment(Environment environment);

    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh() throws BeansException, IllegalStateException;

    void close();

    boolean isActive();

}
