package com.epam.jdi.tools;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.util.List;

public final class CalculationUtils {
    private CalculationUtils() { }
    public static double average(List<Long> collection) {
        if (collection == null || collection.size() == 0)
            return 0;
        if (collection.size() == 1)
            return collection.get(0);
        double average = collection.get(0);
        for (int i = 1; i < collection.size(); i++)
            average = i * (average + collection.get(i).doubleValue() / i) / (i + 1);
        return average;
    }

}