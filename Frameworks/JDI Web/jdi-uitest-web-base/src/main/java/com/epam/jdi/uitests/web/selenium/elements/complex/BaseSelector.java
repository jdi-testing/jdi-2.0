package com.epam.jdi.uitests.web.selenium.elements.complex;

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.complex.IBaseSelector;
import com.epam.jdi.uitests.web.selenium.elements.actions.WebStatic;
import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.first;
import static com.epam.jdi.tools.LinqUtils.firstIndex;
import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

/**
 * Created by Roman_Iovlev on 11/14/2017.
 */
public class BaseSelector extends BaseElement implements IBaseSelector {
    @JDIAction(level = DEBUG)
    public List<WebElement> getWebElements(Object... args) {
        return getElements(args);
    }

    public boolean isSelected(WebElement el) {
        return WebStatic.isSelected(el);
    }
    @Override
    public boolean isSelected(String name) {

        WebElement element = first(getWebElements(), this::isSelected);
        if (element == null)
            throw exception("No elements selected (search for '%s'). Override getSelectedAction or place locator to <select> tag", name);
        // TODO DEMO MODE
        // new Element().setWebElement(element).invoker.processDemoMode();
        return element.getText().equals(name);
    }
    @Override
    public boolean isSelected(int index) {
        if (index <= 0)
            throw exception("Check isSelected by index failed. Index '%s' must be more than 0", index);
        List<WebElement> els = getWebElements();
        if (index > els.size())
            throw exception("Check isSelected by index failed. Index '%s' more than amount of found element '%s' less than ", index, els.size());
        int firstIndex = firstIndex(els, this::isSelected) + 1;
        if (firstIndex == 0)
            throw exception("No elements selected (search for '%s'). Override getSelectedAction or place locator to <select> tag", index);
        // TODO DEMO MODE
        // new Element().setWebElement(webElements(o).get(firstIndex)).invoker.processDemoMode();
        return firstIndex == index;
    }
}
