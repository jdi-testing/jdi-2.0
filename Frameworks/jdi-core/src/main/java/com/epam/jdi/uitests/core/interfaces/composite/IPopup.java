package com.epam.jdi.uitests.core.interfaces.composite;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;
import com.epam.jdi.uitests.core.interfaces.base.IComposite;
import com.epam.jdi.uitests.core.interfaces.common.IText;

import static com.epam.jdi.uitests.core.actions.base.Functions.*;
import static com.epam.jdi.uitests.core.actions.base.UIUtils.getButton;

public interface IPopup extends IText, IComposite {
    /**
     * Click on Button marked with annotation @OkButton or named "okButton"
     */
    @JDIAction
    default void ok() { getButton(this, OK).click(); }

    /**
     * Click on Button marked with annotation @CancelButton or named "cancelButton"
     */
    @JDIAction
    default void cancel() { getButton(this, CANCEL).click();}

    /**
     * Click on Button marked with annotation @CloseButton or named "closeButton"
     */
    @JDIAction
    default void close() { ((IClickable)linked().get("close")).click();}
}