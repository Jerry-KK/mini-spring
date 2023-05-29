package com.minis.inject;

/**
 * Setter 注入属性类
 * @author lethe
 * @date 2023/5/29 23:19
 */
public class PropertyValue {
    private final String name;
    private final String type;
    private final Object value;

    public PropertyValue(String name,String type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
