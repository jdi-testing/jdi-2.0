package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

/**
 * Interface for element with parent
 */
public interface IHasParent {
    /**
     * @param parent Specify parent for element
     * @return this
     */
    <T> T setParent(Object parent);

    /**
     * @return Parent Object
     */
    Object getParent();
}
