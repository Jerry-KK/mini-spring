package com.minis.events;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lethe
 * @date 2023/6/2 15:24
 */
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher{

    List<ApplicationListener> listeners = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        for(ApplicationListener listener : listeners) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.listeners.add(listener);
    }


}
