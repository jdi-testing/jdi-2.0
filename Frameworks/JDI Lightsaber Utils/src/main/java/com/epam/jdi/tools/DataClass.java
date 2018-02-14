package com.epam.jdi.tools;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction1;
import com.epam.jdi.tools.map.MapArray;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DataClass<T> {
    public T set(JAction1<T> valueFunc) {
        T thisObj = (T)this;
        valueFunc.execute(thisObj);
        return thisObj;
    }
    public Map<String, Object> getFieldsAsMap() {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields)
            try {
                map.put(field.getName(), field.get(this));
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        return map;
    }
    public MapArray<String, Object> getFields() {
        Field[] fields = getClass().getDeclaredFields();
        return new MapArray<>(fields, f -> f.getName(), f -> f.get(this));
    }

    @Override
    public String toString() {
        return PrintUtils.printFields(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        try {
            Field[] otherFields = getClass().getDeclaredFields();
            for (Field f : getClass().getDeclaredFields()) {
                Field fOther = LinqUtils.first(otherFields, fo -> fo.getName().equals(f.getName()));
                if (f.get(this) == null && fOther.get(o) == null)
                    continue;
                if (f.get(this) != null && fOther.get(o) == null ||
                    f.get(this) == null && fOther.get(o) != null ||
                    !f.get(this).equals(fOther.get(o)))
                    return false;
                }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (Field f : getClass().getDeclaredFields())
            try { result += 31 * result + f.get(this).hashCode();
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        return result;
    }
}
