package com.epam.jdi.uitests.core.interfaces.composite;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;
import com.epam.jdi.uitests.core.interfaces.base.IComposite;
import com.epam.jdi.uitests.core.interfaces.common.IText;

public interface IPopup extends IText, IComposite {
    /**
     * Click on Button marked with annotation @OkButton or named "okButton"
     */
    @JDIAction
    default void ok() { ((IClickable)linked().get("ok")).click(); }

    /**
     * Click on Button marked with annotation @CancelButton or named "cancelButton"
     */
    @JDIAction
    default void cancel() { ((IClickable)linked().get("cancel")).click(); ;}

    /**
     * Click on Button marked with annotation @CloseButton or named "closeButton"
     */
    @JDIAction
    default void close() { ((IClickable)linked().get("close")).click(); ;}
}