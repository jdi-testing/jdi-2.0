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
     * Set parent to the element
     *
     * @param parent Specify parent for element
     * @return This element
     */
    <T> T setParent(Object parent);

    /**
     * Get parent of the element
     *
     * @return Parent Object
     */
    Object getParent();
}
