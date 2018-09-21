package com.epam.jdi.uitests.web.selenium.elements.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IHasParent;
import org.openqa.selenium.WebElement;

import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;

/**
 * Interface to get and set WebElements
 */
public interface IHasElement extends IHasParent {
    /**
     * Gets element
     * @return WebElement
     */
    @JDIAction(level = DEBUG)
    WebElement getWebElement();

    /**
     * Sets element
     * @param webElement WebElement
     * @param <T> type
     * @return Element
     */
    @JDIAction(level = DEBUG)
    <T extends Element> T setWebElement(WebElement webElement);
}
