package com.epam.jdi.tools;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc;

public class CacheValue<T> {
    public boolean useCache = true;
    private T value;
    private JFunc<T> getRule;
    public CacheValue(JFunc<T> getRule) { this.getRule = getRule; }
    public T get() {
        if (!useCache) return getRule.execute();
        if (value == null)
            value = getRule.execute();
         return value;
    }
    public void set(T value) { this.value = value; }
    public void setRule(JFunc<T> getRule) { this.getRule = getRule; }
    public void clear() { value = null; }
    public boolean hasValue() { return value != null;}
}
