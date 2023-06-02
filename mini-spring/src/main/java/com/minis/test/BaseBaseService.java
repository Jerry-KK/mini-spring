package com.minis.test;

/**
 * @author lethe
 * @date 2023/5/31 13:05
 */
public class BaseBaseService {

    private AServiceImpl as;

    public BaseBaseService() {
    }

    public void setAs(AServiceImpl as) {
        this.as = as;
    }

    public AServiceImpl getAs() {
        return as;
    }

    public void sayHello() {
        System.out.println("Base Base Service says Hello");
        as.sayHello();
    }
}
