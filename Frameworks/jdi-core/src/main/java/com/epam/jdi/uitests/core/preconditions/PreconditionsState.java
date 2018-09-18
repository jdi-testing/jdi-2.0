package com.epam.jdi.uitests.core.preconditions;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.lang.reflect.Method;

import static com.epam.jdi.uitests.core.settings.JDIData.testName;
import static com.epam.jdi.uitests.core.settings.JDISettings.asserter;
import static com.epam.jdi.uitests.core.settings.JDISettings.logger;
import static java.lang.String.format;

public final class PreconditionsState {
    public static boolean alwaysMoveToCondition;
    private PreconditionsState() { }

    /**
     * Checks if test method reached exact state
     * @param condition - condition to be reached
     * @param method - test method that should be checked
     */
    public static void isInState(IPreconditions condition, Method method) {
        try {
            logger.info("Move to condition: " + condition);
            if (method != null) testName = method.getName();
            if (!alwaysMoveToCondition && condition.checkAction())
                return;
            condition.moveToAction();
            asserter.isTrue(condition::checkAction);
            logger.info(condition + " condition achieved");
        } catch (Exception ex) {
            throw asserter.exception(format("Can't reach state: %s. Reason: %s", condition, ex.getMessage()));
        }
    }

    /**
     * Checks if test reached exact state
     * @param condition
     */
    public static void isInState(IPreconditions condition) {
        isInState(condition, null);
    }

    /**
     * Moves test to exact condition
     * @param condition - condition to be reached
     * @param method - test method that should reach condition
     */
    public static void moveToState(IPreconditions condition, Method method) {
        try {
            boolean temp = alwaysMoveToCondition;
            alwaysMoveToCondition = true;
            isInState(condition, method);
            alwaysMoveToCondition = temp;
        } catch (Exception ex) {
            throw asserter.exception(format("Can't reach state: %s. Reason: %s", condition, ex.getMessage()));
        }
    }

    /**
     * Moves test to exact condition
     * @param condition - condition to be reached
     */
    public static void moveToState(IPreconditions condition) {
        moveToState(condition, null);
    }
}