package com.epam.jdi.uitests.core.preconditions;
/* The MIT License
 *
 * Copyright 2004-2017 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */

/**
 * Created by Roman Iovlev on 10.03.2017
 */
import java.lang.reflect.Method;

import static com.epam.jdi.uitests.core.settings.JDIData.testName;
import static com.epam.jdi.uitests.core.settings.JDISettings.asserter;
import static com.epam.jdi.uitests.core.settings.JDISettings.logger;
import static java.lang.String.format;

public final class PreconditionsState {
    public static boolean alwaysMoveToCondition;
    private PreconditionsState() { }

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

    public static void isInState(IPreconditions condition) {
        isInState(condition, null);
    }

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

    public static void moveToState(IPreconditions condition) {
        moveToState(condition, null);
    }
}