package com.minis.beans.factory.config;

import java.util.*;

/**
 * @author lethe
 * @date 2023/5/29 23:29
 */
public class ConstructorArgumentValues {
    private final Map<Integer, ConstructorArgumentValue> indexedArgumentValues = new HashMap<>(0);
    private final List<ConstructorArgumentValue> genericArgumentValues = new LinkedList<>();

    public ConstructorArgumentValues() {
    }

    private void addArgumentValue(Integer key, ConstructorArgumentValue newValue) {
        this.indexedArgumentValues.put(key, newValue);
    }

    public boolean hasIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.containsKey(index);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    public void addGenericArgumentValue(Object value, String type) {
        this.genericArgumentValues.add(new ConstructorArgumentValue(value, type));
    }

    private void addGenericArgumentValue(ConstructorArgumentValue newValue) {
        if (newValue.getName() != null) {
            for (Iterator<ConstructorArgumentValue> it = genericArgumentValues.iterator(); it.hasNext(); ) {
                ConstructorArgumentValue currentArgumentValue = it.next();
                if (newValue.getName().equals(currentArgumentValue.getName())) {
                    it.remove();
                }
            }
        }
        genericArgumentValues.add(newValue);
    }

    public ConstructorArgumentValue getGenericArgumentValue(String requiredName) {
        for (ConstructorArgumentValue valueHolder : this.genericArgumentValues) {
            if (valueHolder.getName() != null && (requiredName == null || !valueHolder.getName().equals(requiredName))) {
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    public int getArgumentCount() {
        return this.genericArgumentValues.size();
    }

    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();
    }


}
