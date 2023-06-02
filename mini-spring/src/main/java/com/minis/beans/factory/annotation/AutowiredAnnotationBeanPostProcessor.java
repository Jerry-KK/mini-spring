package com.minis.beans.factory.annotation;

import com.minis.exceptions.BeansException;

import java.lang.reflect.Field;

/**
 * @author lethe
 * @date 2023/6/2 12:20
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor{

    private AutowireCapableBeanFactory beanFactory;

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;
        Class<?> aClass = bean.getClass();
        Field[] fields = aClass.getDeclaredFields();
        if(fields != null) {
            //对每一个属性进行判断，如果带有@Autowired注解则进行处理
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if(isAutowired) {
                    //根据属性名查找同名的bean
                    String fieldName = field.getName();
                    Object autowiredObj = this.beanFactory.getBean(fieldName);
                    try {
                        field.setAccessible(true);
                        field.set(result, autowiredObj);
                        System.out.println("autowire " + fieldName + " for bean " + beanName);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
