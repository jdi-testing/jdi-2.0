package com.epam.jdi.uitests.web.selenium.elements.composite;

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;
import com.epam.jdi.uitests.core.interfaces.composite.IPopup;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import org.openqa.selenium.Alert;

import static com.epam.jdi.uitests.core.actions.base.Functions.CANCEL;
import static com.epam.jdi.uitests.core.actions.base.Functions.OK;
import static com.epam.jdi.uitests.core.actions.base.UIUtils.getButton;

public class ConfirmationDialog extends Element implements IPopup {

    private Alert alert() {
        return getDriver().switchTo().alert();
    }
    /**
     * Click on Button marked with annotation @OkButton or named "okButton"
     */
    @JDIAction
    public void ok() { alert().accept(); }

    /**
     * Click on Button marked with annotation @CancelButton or named "cancelButton"
     */
    @JDIAction
    public void cancel() { alert().dismiss();}

    /**
     * Click on Button marked with annotation @CloseButton or named "closeButton"
     */
    @JDIAction
    public void close() { cancel(); }
}
