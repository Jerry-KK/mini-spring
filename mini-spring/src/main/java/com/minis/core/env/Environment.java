package com.minis.core.env;

/**
 * @author lethe
 * @date 2023/6/2 14:30
 */

/**
 * 环境
 */
public interface Environment {
    String[] getActiveProfiles();
    String[] getDefaultProfiles();
    boolean acceptsProfiles(String... profiles);
}
