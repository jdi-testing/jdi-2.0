package com.epam.jdi.uitests.web.selenium.elements.apiInteract;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.CacheValue;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.base.IEngine;
import com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory;
import com.epam.jdi.uitests.web.selenium.driver.get.DriverData;
import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.epam.jdi.tools.LinqUtils.filter;
import static com.epam.jdi.tools.ReflectionUtils.isClass;
import static com.epam.jdi.tools.StringUtils.format;
import static com.epam.jdi.uitests.core.settings.JDISettings.*;
import static com.epam.jdi.uitests.core.templates.base.TBaseElement.FAILED_TO_FIND_ELEMENT_MESSAGE;
import static com.epam.jdi.uitests.core.templates.base.TBaseElement.FIND_TO_MUCH_ELEMENTS_MESSAGE;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverByUtils.*;
import static com.epam.jdi.uitests.web.selenium.driver.get.DriverData.DRIVER_NAME;
import static com.epam.jdi.uitests.web.selenium.elements.apiInteract.LocatorType.DEFAULT;
import static com.epam.jdi.uitests.web.selenium.elements.apiInteract.LocatorType.FRAME;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.hasDomain;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * WebEngine implementation of IEngine interface
 */
public class WebEngine implements IEngine {
    private By byLocator;
    private LocatorType locatorType = DEFAULT;
    private BaseElement element;
    private CacheValue<WebElement> webElement;
    private CacheValue<List<WebElement>> webElements;
    private String driverName = "";

    public JFunc1<WebElement, Boolean> localElementSearchCriteria = null;

    /**
     * Constructs WebEngine with element
     * @param element element
     */
    public WebEngine(BaseElement element) {
        this(element, null, DEFAULT);
    }

    /**
     * Constructs WebEngine with element and locator
     * @param element element
     * @param byLocator locator
     */
    public WebEngine(BaseElement element, By byLocator) {
        this(element, byLocator, DEFAULT);
    }

    /**
     * Constructs WebEngine with element, locator and locatorType
     * @param element element
     * @param byLocator locator
     * @param locatorType locatorType
     */
    public WebEngine(BaseElement element, By byLocator, LocatorType locatorType) {
        this.element = element;
        if (getDriverName().equals(""))
            setDriverName(DRIVER_NAME);
        this.byLocator = byLocator;
        this.locatorType = locatorType;
        webElement = new CacheValue<>(this::getWebElement);
        webElement.useCache(useCache);
        webElements = new CacheValue<>(this::getElements);
        webElements.useCache(useCache);
    }

    /**
     * Sets useCache option
     * @param useCache useCache
     */
    public void setUseCache(boolean useCache) {
        webElement.useCache(useCache);
        webElements.useCache(useCache);
    }

    /**
     * Copies WebElement by locator
     * @param locator
     * @return WebElement
     */
    // Copy
    public WebEngine copy(By locator) {
        locatorType = DEFAULT;
        byLocator = locator;
        return copy();
    }

    /**
     * Copies WebElement
     * @param element element
     * @return WebElement
     */
    public WebEngine copy(WebElement element) {
        webElement.setForce(element);
        return copy();
    }

    /**
     * Copies WebElement
     * @return WebElement
     */
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

    /**
     * Returns locator
     * @return locator
     */
    // Locator
    public By getLocator() {
        if (locatorType == FRAME) return null;
        if (isEmpty(locatorArgs)) return byLocator;
        return locatorArgs.length == 1
            ? fillByTemplate(byLocator, locatorArgs)
            : fillByMsgTemplate(byLocator, locatorArgs);
    }

    /**
     * Sets locator
     * @param locator locator
     */
    public void setLocator(By locator) { locatorType = DEFAULT; byLocator = locator; }

    /**
     * Checks whether locator is not null
     * @return true if locator is not null, otherwise false
     */
    public boolean hasLocator() {
        return getLocator() != null;
    }

    /**
     * Returns frame
     * @return locator
     */
    public By getFrame() { return locatorType == FRAME ? byLocator : null; }

    /**
     * Checks whether there's a frame
     * @return true if there's a frame. otherwise false
     */
    public boolean hasFrame() { return getFrame() != null; }

    /**
     * Sets frame
     * @param frame locator
     */
    public void setFrame(By frame) { locatorType = FRAME; byLocator = frame; }
    private Object[] locatorArgs;

    /**
     * Checks whether element has value
     * @return true is element has value
     */
    //Element
    public boolean hasElement() { return webElement.hasValue(); }

    /**
     * Returns isUseCache
     * @return isUseCache
     */
    public boolean isUseCache() { return webElement.isUseCache(); }

    /**
     * Sets WebElement
     * @param webElement WebElement
     */
    public void setWebElement(WebElement webElement) { this.webElement.setForce(webElement); }

    /**
     * Sets WebElements
     * @param webElements WebElements
     */
    public void setWebElements(List<WebElement> webElements) { this.webElements.setForce(webElements); }

    /**
     * Gets WebElement
     * @param args arguments
     * @return WebElement
     */
    public WebElement getWebElement(Object... args) {
        if (hasElement()) return webElement.get();
        if (isNotEmpty(args)) locatorArgs = args;
        int timeout = timeouts.getCurrentTimeoutSec();
        List<WebElement> result = getElements(args);
        switch (result.size()) {
            case 0:
                throw exception(FAILED_TO_FIND_ELEMENT_MESSAGE, element, timeout);
            case 1:
                return result.get(0);
            default:
                throw exception(FIND_TO_MUCH_ELEMENTS_MESSAGE, result.size(), element, timeout);
        }
    }

    /**
     * Searches for all
     * @return WebEngine
     */
    public WebEngine searchAll() {
        localElementSearchCriteria = Objects::nonNull;
        return this;
    }

    /**
     * Ges elements
     * @param args arguments
     * @return list of WebElements
     */
    private List<WebElement> getElements(Object... args) {
        List<WebElement> els = findElements(args);
        return filter(els, el -> element.getSearchCriteria().invoke(el));
    }

    /**
     * Finds elements
     * @param args arguments
     * @return list of WebElements
     */
    public List<WebElement> findElements(Object... args) {
        if (webElements.hasValue()) return webElements.get(ArrayList::new);
        if (isNotEmpty(args)) locatorArgs = args;
        SearchContext searchContext = getDefaultContext();
        if (!containsRoot(getLocator()))
            searchContext = getSearchContext(element.getParent());
        return webElements.set(searchContext.findElements(correctLocator(getLocator())));
    }

    /**
     * Gets search context
     * @param element element
     * @return SearchContext
     */
    private SearchContext getSearchContext(Object element) {
        if (element == null || !isClass(element.getClass(), BaseElement.class))
            return getDefaultContext();
        BaseElement bElement = (BaseElement) element;
        if (bElement.isUseCache() && isClass(bElement.getClass(), Element.class)) {
            Element el = (Element) bElement;
            if (el.engine().hasElement())
                return el.engine().webElement.get();
        }
        Object parent = bElement.getParent();
        By locator = bElement.getLocator();
        By frame = bElement.engine().getFrame();
        SearchContext searchContext = frame != null
            ? getFrameContext(frame)
            : parent == null || containsRoot(locator)
                ? getDefaultContext()
                : getSearchContext(parent);
        return locator != null
            ? searchContext.findElement(correctLocator(locator))
            : searchContext;
    }

    /**
     * Returns correct locator
     * @param locator locator
     * @return locator
     */
    private By correctLocator(By locator) {
        if (locator == null) return null;
        return correctXPaths(containsRoot(locator)
            ? trimRoot(locator)
            : locator);
    }

    /**
     * Gets default context
     * @return SearchContext
     */
    private SearchContext getDefaultContext() {
        return getDriver().switchTo().defaultContent();
    }

    /**
     * Returns frame context
     * @param frame frame
     * @return SearchContext
     */
    private SearchContext getFrameContext(By frame) {
        return getDriver().switchTo().frame(getDriver().findElement(frame));
    }

    /**
     * Returns driver
     * @return
     */
    // Driver
    public WebDriver getDriver() {
        return WebDriverFactory.getDriver(driverName);
    }

    /**
     * Returns driver name
     * @return driver name
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * Sets driver name
     * @param driverName driver name
     */
    public void setDriverName(String driverName) { this.driverName = driverName; }

    /**
     * Prints full locator
     * @return locator
     */
    // print text
    private String printFullLocator() {
        String isFrame = "";
        By locator = getLocator();
        if (hasFrame()) {
            isFrame = "Frame: ";
            locator = getFrame();
        }
        String parent = element.printContext();
        return isBlank(parent)
                ? isFrame + shortBy(locator)
                : parent + ">" + isFrame + shortBy(locator);
    }

    /**
     * ToString override
     * @return formatted locator
     */
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