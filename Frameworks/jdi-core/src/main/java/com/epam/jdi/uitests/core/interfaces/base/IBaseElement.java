package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
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
    @JDIAction(value = "Wait action", level = DEBUG)
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