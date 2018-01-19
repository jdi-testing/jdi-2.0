package com.epam.jdi.tools;

import com.epam.jdi.tools.func.JAction1;
import com.epam.jdi.tools.func.JFunc1;

/**
 * Created by Roman_Iovlev on 1/14/2018.
 */
public class Case<T> {
    public JFunc1<T, Boolean> condition;
    public JAction1<T> action;
    public Case(JFunc1<T, Boolean> condition, JAction1<T> action) {
        this.condition = condition;
        this.action = action;
    }
}
