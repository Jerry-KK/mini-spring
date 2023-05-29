package com.minis.events;

/**
 * @author lethe
 * @date 2023/5/29 23:07
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
