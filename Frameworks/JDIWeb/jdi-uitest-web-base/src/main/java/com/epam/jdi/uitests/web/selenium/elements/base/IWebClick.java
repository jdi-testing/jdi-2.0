package com.epam.jdi.uitests.web.selenium.elements.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;
import org.openqa.selenium.interactions.Actions;

import static com.epam.jdi.uitests.web.selenium.elements.actions.WebStatic.*;

/**
 * Interface to click on element
 */
public interface IWebClick extends IClickable {
    /**
     * Clicks with javascript
     */
    @JDIAction("Click on element")
    default void clickJS() {
        js(this).executeScript("arguments[0].click();", webElement(this));
    }

    /**
     * Clicks at coordinates
     * @param x X
     * @param y Y
     */
    @JDIAction("Click on coordinates (x,y) = ({0}, {1})")
    default void clickByXY(int x, int y) {
        new Actions(driver(this)).moveToElement(webElement(this), x, y)
            .click().build().perform();
    }
}