package com.epam.jdi.uitests.core.reporting;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.map.MapArray;

import java.util.List;

import static com.epam.jdi.tools.CalculationUtils.average;
import static com.epam.jdi.uitests.core.reporting.ActionsType.JDI_ACTION;

/**
 * The {@code PerformanceStatistic} class provides methods
 * for adding statistics with actions duration and printing the average actions time
 */
public final class PerformanceStatistic {
    private static MapArray<ActionsType, List<Long>> statistic = new MapArray<>();

    /**
     * PerformanceStatistic private constructor
     */
    private PerformanceStatistic() {
    }

    /**
     * Adds a duration record for a JDI_ACTION action
     * @param time duration of a JDI_ACTION
     */
    public static void addStatistic(long time) {
        addStatistic(JDI_ACTION, time);
    }

    /**
     * Adds a duration record for an action.
     * Appends the action duration to the end of a durations list for the stated action type
     * @param actionType action type
     * @param time action duration
     */
    public static void addStatistic(ActionsType actionType, long time) {
        if (statistic.keys().contains(actionType))
            statistic.get(actionType).add(time);
    }

    /**
     * Returns average actions time for each action type
     * @return average actions time
     */
    public static String printStatistic() {
        return "Average Actions Time: " + statistic.toMapArray(value -> Double.toString(average(value)));
    }

}