package com.minis.test;

/**
 * @author lethe
 * @date 2023/5/29 21:36
 */
public class AServiceImpl implements AService {

    private String property1;
    private String name;
    private int level;

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public void sayHello() {
        System.out.println("a service 1 say hello");
    }
}