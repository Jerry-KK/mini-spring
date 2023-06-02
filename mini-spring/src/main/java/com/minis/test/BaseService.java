package com.minis.test;

import com.minis.beans.factory.annotation.Autowired;

/**
 * @author lethe
 * @date 2023/5/31 13:05
 */
public class BaseService {

    @Autowired
    private BaseBaseService bbs;

    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }

    public BaseBaseService getBbs() {
        return bbs;
    }

    public void sayHello() {
        System.out.println("Base Service says Hello");
        bbs.sayHello();
    }
}
