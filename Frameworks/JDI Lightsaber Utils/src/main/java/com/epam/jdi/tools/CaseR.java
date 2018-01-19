package com.epam.jdi.tools;

import com.epam.jdi.tools.func.JFunc1;

/**
 * Created by Roman_Iovlev on 1/14/2018.
 */
public class CaseR<T, R> {
    public JFunc1<T, Boolean> condition;
    public JFunc1<T, R> result;
    public CaseR(JFunc1<T, Boolean> condition, JFunc1<T, R> result) {
        this.condition = condition;
        this.result = result;
    }
}
