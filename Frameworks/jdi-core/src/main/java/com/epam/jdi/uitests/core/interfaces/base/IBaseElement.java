package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.uitests.core.actions.base.ElementActions;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.templates.base.LinkedElements;

import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;

/**
 * Interface for base behaviours of elements
 */
public interface IBaseElement extends IHasParent, INamed, IVisible {
    /**
     * Set type name
     *
     * @param typeName Specify type name
     */
    void setTypeName(String typeName);

    /**
     * Print current context
     *
     * @return Current context as String
     */
    default String printContext() {
        return ElementActions.printContext.execute(this);
    }

    /**
     * Get engine
     *
     * @return Current engine
     */
    IEngine engine();

    /**
     * Set timeout
     *
     * @param timoutSec Specify current timeout
     * @return This element
     */
    <T extends IBaseElement> T withTimeout(int timoutSec);

    /**
     * Set driver name
     *
     * @param driverName Specify current driver name
     */
    void setDriverName(String driverName);

    /**
     * Get useCache parameter
     *
     * @return Does current engine uses caching or not
     */
    boolean isUseCache();

    /**
     * Set useCache parameter
     *
     * @param useCache Specify using cache parameter for current engine
     */
    void setUseCache(boolean useCache);

    /**
     * Wait condition
     */
    @JDIAction(value = "Wait action", level = DEBUG)
    default void wait(JFunc<Boolean> condition) {
        ElementActions.wait.execute(this, condition);
    }

    /**
     * Wait condition
     */
    @JDIAction(value = "Wait result")
    default <R> R wait(JFunc<R> get, JFunc1<R, Boolean> condition) {
        return (R) ElementActions.getResultWithCondition.execute(this, get::execute, r -> condition.execute((R) r));
    }

    /**
     * Get LinkedElements
     *
     * @return Linked Elements
     */
    LinkedElements linked();
    // TODO should = wait
    // TODO verifyView
    // TODO label
}