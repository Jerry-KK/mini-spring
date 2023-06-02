package com.minis.beans.factory.config;

/**
 * Setter 注入属性类
 * @author lethe
 * @date 2023/5/29 23:19
 */
public class PropertyValue {
    private final String name;
    private final String type;
    private final Object value;
    private final boolean isRef;

    public PropertyValue(String name, String type, Object value, boolean isRef) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.isRef = isRef;
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

    public boolean isRef() {
        return isRef;
    }
}
