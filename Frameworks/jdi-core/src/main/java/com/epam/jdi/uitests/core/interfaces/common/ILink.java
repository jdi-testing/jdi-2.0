package com.epam.jdi.uitests.core.interfaces.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;

import java.net.URL;

import static com.epam.jdi.tools.TryCatchUtil.tryGetResult;
import static com.epam.jdi.uitests.core.actions.common.LinkActions.getRef;
import static com.epam.jdi.uitests.core.actions.common.LinkActions.getTooltip;

/**
 * Interface for Links
 */
public interface ILink extends IClickable, IText {
    /**
     * @return Get link destination
     */
    @JDIAction
    default String getReference() {
        return getRef.execute(this);
    }

    /**
     * @return Get URL
     */
    @JDIAction
    default URL getURL() {
        return tryGetResult(() -> new URL(getReference()));
    }

    /**
     * @return Get links tooltip
     */
    @JDIAction
    default String getTooltip() {
        return getTooltip.execute(this);
    }
}