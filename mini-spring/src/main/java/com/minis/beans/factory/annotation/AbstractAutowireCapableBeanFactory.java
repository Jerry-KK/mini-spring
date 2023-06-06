package com.minis.beans.factory.annotation;

import com.minis.beans.factory.AutowireCapableBeanFactory;
import com.minis.beans.factory.support.AbstractBeanFactory;
import com.minis.exceptions.BeansException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lethe
 * @date 2023/6/2 12:27
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }


    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (AutowiredAnnotationBeanPostProcessor beanProcessor : beanPostProcessors) {
            beanProcessor.setBeanFactory(this);
            beanProcessor.postProcessBeforeInitialization(result, beanName);
            //todo 下面判断似乎无意义
            if (result == null) {
                return result;
            }
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (AutowiredAnnotationBeanPostProcessor beanProcessor : beanPostProcessors) {
            beanProcessor.setBeanFactory(this);
            beanProcessor.postProcessAfterInitialization(result, beanName);
            //todo 下面判断似乎无意义
            if (result == null) {
                return result;
            }
        }
        return result;
    }
}
