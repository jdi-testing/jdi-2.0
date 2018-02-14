package com.epam.jdi.tools;

import com.epam.jdi.tools.func.JFunc1;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

public class CaseR<T, R> {
    public JFunc1<T, Boolean> condition;
    public JFunc1<T, R> result;
    public CaseR(JFunc1<T, Boolean> condition, JFunc1<T, R> result) {
        this.condition = condition;
        this.result = result;
    }
}
