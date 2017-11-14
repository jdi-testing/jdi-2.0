package com.epam.jdi.uitests.web.selenium.elements.base;

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IHasParent;
import org.openqa.selenium.WebElement;

import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;

/**
 * Created by Roman_Iovlev on 8/31/2016.
 */
public interface IHasElement extends IHasParent {
    @JDIAction(level = DEBUG)
    WebElement getWebElement();
    @JDIAction(level = DEBUG)
    <T extends Element> T setWebElement(WebElement webElement);
}
