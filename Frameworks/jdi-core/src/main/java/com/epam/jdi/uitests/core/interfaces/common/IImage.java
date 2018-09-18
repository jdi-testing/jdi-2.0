package com.epam.jdi.uitests.core.interfaces.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;

import static com.epam.jdi.uitests.core.actions.common.ImageActions.getAlt;
import static com.epam.jdi.uitests.core.actions.common.ImageActions.getSource;

/**
 * Interface for images
 */
public interface IImage extends IClickable {
    /**
     * @return Get image source
     */
    @JDIAction
    default String getSource() {
        return getSource.execute(this);
    }

    /**
     * @return Get image alt/hint text
     */
    @JDIAction
    default String getAlt() {
        return getAlt.execute(this);
    }
}