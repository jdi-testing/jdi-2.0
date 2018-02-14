package com.epam.jdi.tools.pairs;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.DataClass;

public class Pair<TFirst, TSecond> extends DataClass {
    public TFirst key;
    public TSecond value;

    public Pair(TFirst value1, TSecond value2) {
        key = value1;
        value = value2;
    }
}