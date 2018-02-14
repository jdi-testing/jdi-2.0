package com.epam.page.object.generator.model;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.epam.jdi.tools.LinqUtils.select;
import static java.lang.String.format;
import static java.lang.String.join;

public class Pairs extends ArrayList<ElementAttribute>{

    public Pairs() { }
    public Pairs(Pairs pairs) {
        for (ElementAttribute attr : pairs)
            add(attr.getName(), attr.getValue());
    }
    public Pairs(List<String> array,
                 Function<String, String> value1func,
                 Function<String, String> value2func) {
        for (String el : array)
            add(new ElementAttribute(value1func.apply(el), value2func.apply(el)));
    }

    public static Pairs merge(Pairs pairs1, Pairs pairs2) {
        Pairs result = new Pairs();
        result.addAll(pairs1);
        result.addAll(pairs2);
        return result;
    }
    public String first(Function<String, Boolean> condition) {
        for (ElementAttribute attr : new Pairs(this))
            if (condition.apply(attr.getName())) {
                return pullValue(attr);
            }
        return null;
    }
    public List<String> filter(Function<String, Boolean> condition) {
        List<String> result = new ArrayList<>();
        for (ElementAttribute attr : new Pairs(this))
            if (condition.apply(attr.getName()))
                result.add(pullValue(attr));
        return result;
    }
    private String pullValue(ElementAttribute pair) {
        remove(pair);
        return pair.getValue();
    }
    public void remove(ElementAttribute attr) {
        for (ElementAttribute pair : this)
            if (pair.getName().equals(attr.getName()) && pair.getValue().equals(attr.getValue())) {
                super.remove(pair);
                return;
            }
    }
    private ElementAttribute pullPair(ElementAttribute pair) {
        remove(pair);
        return pair;
    }
    public String printAsXpath() {
        return print(" and ", "%s='%s'");
    }
    public String print(String separator, String format) {
        return join(separator, select(this,
        el -> format(format, el.getName(), el.getValue())));
    }
    public Pairs add(String name, String value) {
        super.add(new ElementAttribute(name, value));
        return this;
    }
}
