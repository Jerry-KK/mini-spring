package com.minis.test;

import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.context.ClassPathXmlApplicationContextOld;
import com.minis.exceptions.BeansException;

/**
 * @author lethe
 * @date 2023/5/31 14:07
 */
public class DemoMain {
    public static void main(String[] args) throws BeansException {
//        ClassPathXmlApplicationContextOld classPathXmlApplicationContextOld = new ClassPathXmlApplicationContextOld("beans.xml");
        ClassPathXmlApplicationContext classPathXmlApplicationContextOld = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("============================");
        BaseService baseService = (BaseService) classPathXmlApplicationContextOld.getBean("baseservice");
        baseService.sayHello();
    }
}
