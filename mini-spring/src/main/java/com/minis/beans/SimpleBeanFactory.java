package com.minis.beans;

import com.minis.exceptions.BeansException;
import com.minis.inject.ArgumentValue;
import com.minis.inject.ArgumentValues;
import com.minis.inject.PropertyValue;
import com.minis.inject.PropertyValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        //先尝试直接拿Bean实例
        Object singleton = singletons.get(beanName);
        //如果此时还没有这个Bean的实例，则获取它的定义来创建实例
        if(singleton == null) {
            if(!beanDefinitionMap.containsKey(beanName)) {
                throw new BeansException();
            } else {
                //获取Bean的定义
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                singleton = createBean(beanDefinition);
                //注册Bean实例
                singletons.put(beanDefinition.getId(), singleton);
            }
        }
        return singleton;
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


    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
            // 处理构造器参数
            ArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            if(argumentValues.isEmpty()) {
                int argumentCount = argumentValues.getArgumentCount();
                Class<?>[] paramTypes = new Class[argumentCount];
                Object[] paramValues = new Object[argumentCount];
                for (int i = 0; i < argumentCount; i++) {
                    ArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
                    if ("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
                        paramTypes[i] = String.class; paramValues[i] = argumentValue.getValue();
                    } else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String)argumentValue.getValue());
                    } else if ("int".equals(argumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    } else {
                        //默认为string
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    }
                }
                //按照特定构造器创建实例
                con = clz.getConstructor(paramTypes);
                obj = con.newInstance(paramValues);
            } else {
                //如果没有参数，直接创建实例
                obj = clz.newInstance();
            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 处理属性
        handleProperties(beanDefinition, clz, obj);
        return obj;
    }

    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
        // 处理属性
        System.out.println("handle properties for bean : " + bd.getId());
        PropertyValues propertyValues = bd.getPropertyValues();
        //如果有属性
        if(!propertyValues.isEmpty()) {
            List<PropertyValue> propertyValueList = propertyValues.getPropertyValueList();
            //对每一个属性，分数据类型分别处理
            for (PropertyValue propertyValue : propertyValueList) {
                String pName = propertyValue.getName();
                String pType = propertyValue.getType();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.isRef();
                Class<?>[] paramTypes = new Class[1];
                Object[] paramValues = new Object[1];
                if(!isRef) {    //如果不是ref，只是普通属性 //对每一个属性，分数据类型分别处理
                    if("String".equals(pType) || "java.lang.String".equals(pType)) {
                        paramTypes[0] = String.class;
                    } else if("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                    } else {
                        // 默认为string
                        paramTypes[0] = String.class;
                    }
                } else {    //is ref, create the dependent beans
                    try {
                        paramTypes[0] = Class.forName(pType);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        paramValues[0] = getBean((String) pValue);
                    } catch (BeansException e) {
                        e.printStackTrace();
                    }
                }
                paramValues[0] = pValue;
                //按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                    method.invoke(obj, paramValues);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
