package com.epam.jdi.uitests.core.reporting;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.map.MapArray;

import java.util.List;

import static com.epam.jdi.tools.CalculationUtils.average;
import static com.epam.jdi.uitests.core.reporting.ActionsType.JDI_ACTION;

public final class PerformanceStatistic {
    private static MapArray<ActionsType, List<Long>> statistic = new MapArray<>();

    private PerformanceStatistic() {
    }

    public static void addStatistic(long time) {
        addStatistic(JDI_ACTION, time);
    }

    public static void addStatistic(ActionsType actionType, long time) {
        if (statistic.keys().contains(actionType))
            statistic.get(actionType).add(time);
    }

    public static String printStatistic() {
        return "Average Actions Time: " + statistic.toMapArray(value -> Double.toString(average(value)));
    }

}