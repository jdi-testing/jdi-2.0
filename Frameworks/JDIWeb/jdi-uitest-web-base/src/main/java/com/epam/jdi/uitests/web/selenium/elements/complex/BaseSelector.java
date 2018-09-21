package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.complex.IBaseSelector;
import com.epam.jdi.uitests.web.selenium.elements.actions.WebStatic;
import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;
import org.openqa.selenium.WebElement;

import java.util.List;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelected;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelectedByIndex;
import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;

/**
 * BaseSelector complex element
 */
public class BaseSelector extends BaseElement implements IBaseSelector {
    /**
     * Gets WebElements
     * @param args
     * @return
     */
    @JDIAction(level = DEBUG)
    public List<WebElement> getWebElements(Object... args) {
        return getElements(args);
    }

    /**
     * Checks whether element is selected
     * @param el element
     * @return true if element is selected, false otherwise
     */
    public boolean isSelected(WebElement el) {
        return WebStatic.isSelected(el);
    }

    /**
     * Checks whether element is selected
     * @param name Specify name using string
     * @return true if element is selected, false otherwise
     */
    @Override
    public boolean isSelected(String name) {
        return isSelected.execute(this, name);
    }

    /**
     * Checks whether element is selected
     * @param index Specify name using enum
     * @return true if element is selected, false otherwise
     */
    @Override
    public boolean isSelected(int index) {
        return isSelectedByIndex.execute(this, index);
    }

    /**
     * Checks whether element is displayed now
     * @return true if element is displayed now, otherwise false
     */
    @Override
    public boolean displayedNow() {
        setWaitTimeout(0);
        boolean result = false;
        try { List<WebElement> els = getWebElements();
            result = els.size() > 0 && LinqUtils.any(els, WebElement::isDisplayed);
        } catch (Exception ex) {}
        restoreWaitTimeout();
        return result;
    }
}
