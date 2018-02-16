package com.epam.jdi.uitests.core.templates.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;

import java.util.HashMap;
import java.util.Map;

import static com.epam.jdi.tools.LinqUtils.foreach;
import static com.epam.jdi.tools.PrintUtils.print;

public class LinkedElements {
    public LinkedElements() {
        linkedElements = new HashMap<>();}
    public LinkedElements(Map<String, IBaseElement> map) {
        this();
        foreach(map, e -> add(e.getKey(), e.getValue()));
    }
    protected Map<String, IBaseElement> linkedElements = new HashMap<>();
    public IBaseElement get(String name) {
        return linkedElements.get(name.toLowerCase());
    }
    public boolean isEmpty() {
        return linkedElements.size() == 0;
    }
    public boolean isAny() {
        return !isEmpty();
    }
    public boolean has(String name) {
        return linkedElements.containsKey(name);
    }
    public void add(String name, IBaseElement element) {
        linkedElements.put(name.toLowerCase(), element);
    }
    @Override
    public String toString() {
        return print(linkedElements);
    }
}
