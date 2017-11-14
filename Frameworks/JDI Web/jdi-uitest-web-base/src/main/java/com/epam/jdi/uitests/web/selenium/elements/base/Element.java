package com.epam.jdi.uitests.web.selenium.elements.base;
/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */


import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IElement;
import com.epam.jdi.uitests.core.settings.JDISettings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static com.epam.jdi.tools.ReflectionUtils.newEntity;
import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

/**
 * Base Element control implementation
 *
 * @author Alexeenko Yan
 * @author Belin Yury
 * @author Belousov Andrey
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class Element extends BaseElement implements IElement, IHasElement {

    @JDIAction(level = DEBUG)
    public WebElement getWebElement() {
        return getElement();
    }
    @JDIAction(level = DEBUG)
    public WebElement getNowWebElement() {
        return getDriver().findElement(getLocator());
    }
    @JDIAction(level = DEBUG)
    public WebElement firstWebElement() {
        List<WebElement> els = engine().findElements();
        if (els.size() > 0)
            return els.get(0);
        else
            throw exception("Can't find element " + toString());
    }

    @JDIAction(level = DEBUG)
    public <T extends Element> T setWebElement(WebElement webElement) {
        engine().setWebElement(webElement);
        return (T) this;
    }

    @JDIAction(level = DEBUG)
    public WebElement findElement(By locator) {
        return getWebElement().findElement(locator);
    }

    @JDIAction(level = DEBUG)
    public List<WebElement> findElements(By locator) {
        return getWebElement().findElements(locator);
    }

    @JDIAction(level = DEBUG)
    public WebElement getInvisibleElement() {
        return engine().searchAll().getWebElement();
    }

    /**
     * @param resultFunc Specify expected function result
     * @param condition  Specify expected function condition
     * @return Waits while condition with WebElement happens and returns result using resultFunc
     */
    @JDIAction
    public <R> R wait(JFunc1<WebElement, R> resultFunc, JFunc1<R, Boolean> condition) {
        return wait(() -> resultFunc.invoke(getWebElement()), condition::execute);
    }

    /**
     * @param resultFunc Specify expected function result
     * @param timeoutSec Specify timeout
     * Waits while condition with WebElement happens during specified timeout and returns wait result
     */
    @JDIAction
    public void wait(JFunc1<WebElement, Boolean> resultFunc, int timeoutSec) {
        try {
            setWaitTimeout(timeoutSec);
            wait(resultFunc, r -> r);
        } finally {
            restoreWaitTimeout();
        }
    }

    /**
     * @param resultFunc Specify expected function result
     * @param timeoutSec Specify timeout
     * @param condition  Specify expected function condition
     * @return Waits while condition with WebElement and returns wait result
     */
    @JDIAction
    public <R> R wait(JFunc1<WebElement, R> resultFunc, JFunc1<R, Boolean> condition, int timeoutSec) {
        setWaitTimeout(timeoutSec);
        R result = wait(() -> resultFunc.invoke(getWebElement()), condition);
        restoreWaitTimeout();
        return result;
    }

    @JDIAction
    public void selectArea(int x1, int y1, int x2, int y2) {
        WebElement element = getWebElement();
        new Actions(getDriver()).moveToElement(element, x1, y1).clickAndHold()
                .moveToElement(element, x2, y2).release().build().perform();
    }

    @JDIAction
    public void dragAndDropBy(int x, int y) {
        new Actions(getDriver()).dragAndDropBy(getWebElement(), x, y).build().perform();
    }

    @JDIAction
    public void dragAndDropTo(Element target) {
        new Actions(getDriver()).dragAndDrop(getWebElement(), target.getWebElement()).build().perform();
    }
    @JDIAction
    public void hover() {
        Actions action = new Actions(getDriver());
        action.moveToElement(getWebElement()) .clickAndHold().build().perform();
    }
    public <T extends Element> T copy(By newLocator) {
        try {
            T result = newEntity((Class<T>) getClass());
            result.setEngine(newLocator, engine());
            return result;
        } catch (Exception ex) {
            throw JDISettings.exception("Can't copy Element: " + this);
        }
    }
    public WebElement get(By locator) {
        Element el = new Element().setLocator(locator).setParent(this);
        return el.getWebElement();
    }
}