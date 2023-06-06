package com.minis.events;

import java.util.EventListener;

/**
 * @author lethe
 * @date 2023/6/2 15:18
 */
public class ApplicationListener implements EventListener {

    void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());
    }
}
