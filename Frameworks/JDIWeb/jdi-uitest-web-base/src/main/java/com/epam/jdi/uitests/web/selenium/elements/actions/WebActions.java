package com.epam.jdi.uitests.web.selenium.elements.actions;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.actions.base.ElementActions;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.complex.BaseSelector;
import com.epam.jdi.uitests.web.selenium.elements.complex.table.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;
import static com.epam.jdi.tools.LinqUtils.select;
import static com.epam.jdi.uitests.core.actions.base.ElementActions.*;
import static com.epam.jdi.uitests.core.actions.common.CheckboxActions.isChecked;
import static com.epam.jdi.uitests.core.actions.common.ClickActions.click;
import static com.epam.jdi.uitests.core.actions.common.ImageActions.getAlt;
import static com.epam.jdi.uitests.core.actions.common.ImageActions.getSource;
import static com.epam.jdi.uitests.core.actions.common.LinkActions.getRef;
import static com.epam.jdi.uitests.core.actions.common.LinkActions.getTooltip;
import static com.epam.jdi.uitests.core.actions.common.TextActions.getText;
import static com.epam.jdi.uitests.core.actions.common.TextFieldActions.clear;
import static com.epam.jdi.uitests.core.actions.common.TextFieldActions.input;
import static com.epam.jdi.uitests.core.actions.complex.ListSelectActions.getClickable;
import static com.epam.jdi.uitests.core.actions.complex.ListSelectActions.getClickableByIndex;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.*;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelected;
import static com.epam.jdi.uitests.core.actions.complex.TextListActions.*;
import static com.epam.jdi.uitests.core.actions.composite.PageActions.openPage;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.web.selenium.elements.actions.WebStatic.*;
import static com.epam.jdi.uitests.web.selenium.elements.actions.WebStatic.isSelected;
import static jdk.nashorn.internal.objects.Global.print;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * WebActions initializator
 */
public abstract class WebActions {
    private static boolean firstInit = true;

    /**
     * Initializes all WebActions
     */
    public static void Init() {
        if (firstInit) {
            initCommonActions();
            initComplexActions();
            initCompositeActions();
            firstInit = false;
        }
    }

    /**
     * Initializes common WebActions
     */
    public static void initCommonActions() {
        eDisplayed = e -> we(e).isDisplayed();
        eGetText = e -> we(e).getText();
        displayed = o -> {
            WebElement el = element(o).getNowWebElement();
            return el != null && el.isDisplayed();
        };
        enabled = o -> webElement(o).isEnabled();
        getAttribute = (o, name) -> webElement(o).getAttribute(name);
        setAttribute = (o, name, value) -> js(o).executeScript(
            "arguments[0].setAttribute('"+name+"',arguments[1]);", webElement(o), value);
        getElement = WebStatic::webElement;
        wait = (o, condition) -> base(o).timer().getResult(condition);
        getResultWithCondition = (o, get, condition) ->
                base(o).timer().getResultByCondition(get, condition);
        getElements = (o, args) -> map(webElements(o), w -> (Object)w);
        ElementActions.focus = o -> {
            Dimension size = webElement(o).getSize(); //for scroll to object
            actions(o).moveToElement(webElement(o), size.width / 2, size.height / 2)
            .build().perform();
        };
        isChecked = o -> isSelected(webElement(o));
        click = o -> webElement(o).click();
        getSource = o -> attribute(o, "src");
        getAlt = o -> attribute(o, "alt");
        getRef = o -> attribute(o, "href");
        getTooltip = o -> attribute(o, "title");
        getText = o -> {
            WebElement el = webElement(o);
            String value = el.getAttribute("value");
            if (!isBlank(value))
                return value;
            String text = el.getText();
            return text != null ? text : value;
        };
        input = (o, keys) -> webElement(o).sendKeys(keys);
        clear = o -> webElement(o).clear();
        getClickable = (o, name) -> {
            List<WebElement> els = webElements(o);
            WebElement element;
            if (els.size() == 0)
                throw exception("Can't find clickable element '%s'. Correct locator no elements found", name);
            if (els.size() == 1 && els.get(0).getTagName().equals("ul"))
                els = els.get(0).findElements(By.tagName("li"));
            if ((element = first(els, e -> e.getText().equals(name))) == null)
                throw exception("Can't find clickable element '%s'. No names match (%s)", name, print(select(els, WebElement::getText)));
            return new Button().setWebElement(element);
        };
        getClickableByIndex = (o, index) -> {
            if (index <= 0)
                throw exception("Check isSelected by index failed. Index '%s' must be more than 0", index);
            List<WebElement> els = webElements(o);
            if (index > els.size())
                throw exception("Check isSelected by index failed. Index '%s' more than amount of found element '%s' less than ", index, els.size());
            return new Button().setWebElement(els.get(index - 1));
        };
    }

    /**
     * Initializes complex WebActions
     */
    public static void initComplexActions() {
        isSelected = (o, name) -> {
            WebElement element = first(webElements(o), el -> el.getText().equals(name));
            if (element == null)
                throw exception("No elements selected (search for '%s'). Override getSelectedAction or place locator to <select> tag", name);
            return ((BaseSelector)o).isSelected(element);
        };
        isSelectedByIndex = (o, index) -> {
            if (index <= 0)
                throw exception("Check isSelected by index failed. Index '%s' must be more than 0", index);
            List<WebElement> els = webElements(o);
            if (index > els.size())
                throw exception("Check isSelected by index failed. Index '%s' more than amount of found element '%s' less than ", index, els.size());
            int firstIndex = firstIndex(els, ((BaseSelector) o)::isSelected) + 1;
            if (firstIndex == 0)
                throw exception("No elements selected (search for '%s'). Override getSelectedAction or place locator to <select> tag", index);
            return firstIndex == index;
        };
        getSelected = o -> first(webElements(o), WebStatic::isSelected).getText();
        getSelectedIndex = o -> firstIndex(webElements(o), WebStatic::isSelected);
        getOptions = o -> map(webElements(o), WebElement::getText);
        getTextByName = (o, name) -> {
            for(WebElement el : webElements(o)) {
                String text = el.getText();
                if (text.contains(name))
                    return text;
            }
            throw exception("Text '%s' not found in text list", name);
        };
        getTextByIndex = (o, index) -> {
            if (index <= 0)
                throw exception("Wrong index %s. Index should be more than 0", index);
            List<WebElement> els = webElements(o);
            if (index < els.size())
                throw exception("Wrong index %s. It should be less than elements count %s", index, els.size());
            return els.get(index).getText();
        };
        getAllText = o -> map(webElements(o), WebElement::getText);
        toCell = (o, el, x, y) ->
            new Cell(x, y, o)
                .setWebElement(element(el).getWebElement());
    }

    /**
     * Initializes composite WebActions
     */
    public static void initCompositeActions() {
        openPage = (o) -> driver(o).navigate().to(page(o).url);
    }
}
