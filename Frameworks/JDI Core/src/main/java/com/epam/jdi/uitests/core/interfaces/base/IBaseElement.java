package com.epam.jdi.uitests.core.interfaces.base;
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

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.templates.base.LinkedElements;

import static com.epam.jdi.uitests.core.actions.base.ElementActions.*;
import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;

public interface IBaseElement extends IHasParent, INamed, IVisible {
    void setTypeName(String typeName);
    default String printContext() { return printContext.execute(this); }
    IEngine engine();
    <T extends IBaseElement> T withTimeout(int timoutSec);
    void setDriverName(String driverName);
    boolean isUseCache();
    void setUseCache(boolean useCache);
    /**
     *  Wait condition
     */
    @JDIAction(value = "Wait action", result = "Done", level = DEBUG)
    default void wait(JFunc<Boolean> condition) { wait.execute(this, condition); }
    /**
     *  Wait condition
     */
    @JDIAction(value = "Wait result")
    default <R> R wait(JFunc<R> get, JFunc1<R, Boolean> condition) {
        return (R) getResultWithCondition.execute(this, () -> get.execute(), r -> condition.execute((R)r));
    }
    LinkedElements linked();
    // TODO should = wait
    // TODO verifyView
    // TODO label
}