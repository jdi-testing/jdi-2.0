package com.epam.jdi.uitests.web.selenium.elements.actions;

import com.epam.jdi.uitests.core.actions.base.ElementActions;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.complex.table.Cell;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;
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
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by Roman_Iovlev on 8/24/2017.
 */
public abstract class WebActions {
    private static boolean firstInit = true;
    public static void Init() {
        if (firstInit) {
            initCommonActions();
            initComplexActions();
            initCompositeActions();
            firstInit = false;
        }
    }

    public static void initCommonActions() {
        eDisplayed = e -> we(e).isDisplayed();
        eGetText = e -> we(e).getText();
        displayed = o -> element(o).getNowWebElement().isDisplayed();
        enabled = o -> webElement(o).isEnabled();
        getAttribute = (o, name) -> webElement(o).getAttribute(name);
        setAttribute = (o, name, value) -> js(o).executeScript(
            "arguments[0].setAttribute('"+name+"',arguments[1]);", webElement(o), value);
        getElement = WebStatic::webElement;
        wait = (o, condition) -> element(o).timer().getResult(condition);
        getResultWithCondition = (o, get, condition) ->
                element(o).timer().getResultByCondition(get, condition);
        getElements = (o, args) -> map(webElements(o, args), w -> (Object)w);
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
            String value = attribute(o, "value");
            if (isBlank(value))
                return value;
            String text = webElement(o).getText();
            return text != null ? text : value;
        };
        input = (o, keys) -> webElement(o).sendKeys(keys);
        clear = o -> webElement(o).clear();
        getClickable = (o, name) -> {
            List<WebElement> els = webElements(o);
            WebElement element;
            if (els.size() == 0 || (element = first(els, e -> e.getText().equals(name))) == null)
                throw exception("Can't find clickable element '%s'", name);
            return new Button().setWebElement(element);
        };
        getClickableByIndex = (o, index) -> {
            if (index <= 0)
                throw exception("Check isSelected by index failed. Index '%s' must be more than 0", index);
            List<WebElement> els = webElements(o);
            if (index > els.size())
                throw exception("Check isSelected by index failed. Index '%s' more than amount of found element '%s' less than ", index, els.size());
            return new Button().setWebElement(els.get(index));
        };
    }

    public static void initComplexActions() {
        isSelected = (o, name) -> {
            WebElement element = first(webElements(o), el -> WebStatic.isSelected(el));
            if (element == null)
                throw exception("No elements selected (search for '%s'). Override getSelectedAction or place locator to <select> tag", name);
            // TODO DEMO MODE
            // new Element().setWebElement(element).invoker.processDemoMode();
            return element.getText().equals(name);
        };
        isSelectedByIndex = (o, index) -> {
            if (index <= 0)
                throw exception("Check isSelected by index failed. Index '%s' must be more than 0", index);
            List<WebElement> els = webElements(o);
            if (index > els.size())
                throw exception("Check isSelected by index failed. Index '%s' more than amount of found element '%s' less than ", index, els.size());
            int firstIndex = firstIndex(els, WebStatic::isSelected) + 1;
            if (firstIndex == 0)
                throw exception("No elements selected (search for '%s'). Override getSelectedAction or place locator to <select> tag", index);
            // TODO DEMO MODE
            // new Element().setWebElement(webElements(o).get(firstIndex)).invoker.processDemoMode();
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

    public static void initCompositeActions() {
        openPage = (o) -> driver(o).navigate().to(page(o).url);
    }
}
