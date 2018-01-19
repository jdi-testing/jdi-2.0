package com.epam.jdi.uitests.web.selenium.elements.apiInteract;
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
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.base.IEngine;
import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

import static com.epam.jdi.tools.LinqUtils.where;
import static com.epam.jdi.tools.ReflectionUtils.isClass;
import static com.epam.jdi.uitests.core.settings.JDISettings.*;
import static com.epam.jdi.uitests.core.templates.base.TBaseElement.FAILED_TO_FIND_ELEMENT_MESSAGE;
import static com.epam.jdi.uitests.core.templates.base.TBaseElement.FIND_TO_MUCH_ELEMENTS_MESSAGE;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverByUtils.*;
import static com.epam.jdi.uitests.web.selenium.elements.apiInteract.LocatorType.DEFAULT;
import static com.epam.jdi.uitests.web.selenium.elements.apiInteract.LocatorType.FRAME;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.getDriverFactory;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.hasDomain;
import static java.lang.String.format;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

/**
 * Created by Roman_Iovlev on 7/3/2015.
 */
public class WebEngine implements IEngine {
    private By byLocator;
    private LocatorType locatorType = DEFAULT;
    private BaseElement element;
    private WebElement webElement;
    private List<WebElement> webElements;
    private String driverName = "";

    public JFunc1<WebElement, Boolean> localElementSearchCriteria = null;

    public WebEngine(BaseElement element) {
        this(element, null, DEFAULT);
    }

    public WebEngine(BaseElement element, By byLocator) {
        this(element, byLocator, DEFAULT);
    }
    public WebEngine(BaseElement element, By byLocator, LocatorType locatorType) {
        this.element = element;
        if (getDriverName().equals(""))
            setDriverName(getDriverFactory().currentDriverName());
        this.byLocator = byLocator;
        this.locatorType = locatorType;
    }

    // Copy
    public WebEngine copy(By locator) {
        locatorType = DEFAULT;
        byLocator = locator;
        return copy();
    }
    public WebEngine copy(WebElement element) {
        webElement = element;
        return copy();
    }
    public WebEngine copy() {
        WebEngine clone = new WebEngine(element);
        clone.locatorType = locatorType;
        clone.byLocator = byLocator;
        clone.localElementSearchCriteria = localElementSearchCriteria;
        clone.setDriverName(driverName);
        clone.element = element;
        clone.webElement = webElement;
        clone.webElements = webElements;
        return clone;
    }

    // Locator
    public By getLocator() {
        if (locatorType == FRAME) return null;
        if (isEmpty(locatorArgs)) return byLocator;
        return locatorArgs.length == 1
            ? fillByTemplate(byLocator, locatorArgs)
            : fillByMsgTemplate(byLocator, locatorArgs);
    }
    public void setLocator(By locator) { locatorType = DEFAULT; byLocator = locator; }
    public boolean hasLocator() {
        return getLocator() != null;
    }
    public By getFrame() { return locatorType == FRAME ? byLocator : null; }
    public boolean hasFrame() { return getFrame() != null; }
    public void setFrame(By frame) { locatorType = FRAME; byLocator = frame; }
    private Object[] locatorArgs;

    //Element
    public boolean hasElement() { return webElement != null; }
    public void setWebElement(WebElement webElement) { this.webElement = webElement; }
    public WebElement getWebElement(Object... args) {
        if (hasElement()) return webElement;
        if (isNotEmpty(args)) locatorArgs = args;
        int timeout = timeouts.getCurrentTimeoutSec();
        List<WebElement> result = getElements();
        switch (result.size()) {
            case 0:
                throw exception(FAILED_TO_FIND_ELEMENT_MESSAGE, element, timeout);
            case 1:
                return result.get(0);
            default:
                throw exception(FIND_TO_MUCH_ELEMENTS_MESSAGE, result.size(), element, timeout);
        }
    }

    public WebEngine searchAll() {
        localElementSearchCriteria = Objects::nonNull;
        return this;
    }
    private List<WebElement> getElements(Object... args) {
        List<WebElement> els = findElements(args);
        return where(els, el -> element.getSearchCriteria().invoke(el));
    }
    public List<WebElement> findElements(Object... args) {
        if (isNotEmpty(args)) locatorArgs = args;
        SearchContext searchContext = getDefaultContext();
        if (!containsRoot(getLocator()))
            searchContext = getSearchContext(element.getParent());
        return searchContext.findElements(correctLocator(getLocator()));
    }

    private SearchContext getSearchContext(Object element) {
        if (element == null || !isClass(element.getClass(), BaseElement.class))
            return getDefaultContext();
        BaseElement bElement = (BaseElement) element;
        if (bElement.isUseCache() && isClass(bElement.getClass(), Element.class)) {
            Element el = (Element) bElement;
            if (el.engine().hasElement())
                return el.engine().webElement;
        }
        Object p = bElement.getParent();
        By locator = bElement.getLocator();
        By frame = bElement.engine().getFrame();
        SearchContext searchContext = frame != null
            ? getFrameContext(frame)
            : p == null || containsRoot(locator)
                ? getDefaultContext()
                : getSearchContext(p);
        return locator != null
            ? searchContext.findElement(correctLocator(locator))
            : searchContext;
    }
    private By correctLocator(By locator) {
        if (locator == null) return null;
        return correctXPaths(containsRoot(locator)
            ? trimRoot(locator)
            : locator);
    }
    private SearchContext getDefaultContext() {
        return getDriver().switchTo().defaultContent();
    }
    private SearchContext getFrameContext(By frame) {
        return getDriver().switchTo().frame(getDriver().findElement(frame));
    }
    // Driver
    public WebDriver getDriver() {
        return getDriverFactory().getDriver(driverName);
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    // print text
    private String printFullLocator() {
        String isFrame = "";
        By locator = getLocator();
        if (hasFrame()) {
            isFrame = "Frame:";
            locator = getFrame();
        }
        return element.printContext() + "; " + isFrame + printShortBy(locator);
    }

    private String printShortBy(By by) {
        return String.format("%s='%s'", getByName(by), getByLocator(by));
    }

    @Override
    public String toString() {
        if (!hasDomain() && !hasFrame())
            return "No Locators";
        return shortLogMessagesFormat
                ? printFullLocator()
                : format("Locator: '%s'", getLocator())
                + (element.getParent() != null && isClass(element.getParent().getClass(), IBaseElement.class)
                ? format(", Context: '%s'", element.printContext())
                : "");
    }
}