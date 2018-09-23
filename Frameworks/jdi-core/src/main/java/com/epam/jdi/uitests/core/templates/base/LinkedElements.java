package com.epam.jdi.uitests.core.templates.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.util.HashMap;
import java.util.Map;

import static com.epam.jdi.tools.LinqUtils.foreach;
import static com.epam.jdi.tools.PrintUtils.print;

public class LinkedElements {
    protected Map<String, Object> linkedElements = new HashMap<>();

    public LinkedElements() {
        linkedElements = new HashMap<>();
    }

    public LinkedElements(Map<String, Object> map) {
        this();
        foreach(map, e -> add(e.getKey().toLowerCase(), e.getValue()));
    }

    /**
     * Returns element by it's name
     * @param name - element's name
     * @return Object
     */
    public Object get(String name) {
        return linkedElements.get(name.toLowerCase());
    }

    /**
     * Checks if map is empty
     * @return
     */
    public boolean isEmpty() {
        return linkedElements.size() == 0;
    }

    /**
     * Checks if map contains any element
     * @return boolean
     */
    public boolean hasAny() {
        return !isEmpty();
    }

    /**
     * Checks if map contains element with exact key
     * @param name - key
     * @return boolean
     */
    public boolean has(String name) {
        return linkedElements.containsKey(name.toLowerCase());
    }

    /**
     * Add element with exact key
     * @param name - element's key
     * @param element - element
     */
    public void add(String name, Object element) {
        linkedElements.put(name.toLowerCase(), element);
    }

    @Override
    public String toString() {
        return print(linkedElements);
    }
}
