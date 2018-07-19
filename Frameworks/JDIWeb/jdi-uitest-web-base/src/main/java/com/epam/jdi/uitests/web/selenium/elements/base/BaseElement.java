package com.epam.jdi.uitests.web.selenium.elements.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.base.IEngine;
import com.epam.jdi.uitests.core.templates.base.TBaseElement;
import com.epam.jdi.uitests.web.selenium.elements.WebCascadeInit;
import com.epam.jdi.uitests.web.selenium.elements.apiInteract.WebEngine;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.epam.jdi.tools.ReflectionUtils.isClass;
import static com.epam.jdi.uitests.core.settings.JDISettings.logger;
import static com.epam.jdi.uitests.core.settings.JDISettings.timeouts;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverByUtils.shortBy;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.SEARCH_CONDITION;
import static org.apache.commons.lang3.StringUtils.isBlank;

public abstract class BaseElement extends TBaseElement {
    public JFunc1<WebElement, Boolean> localSearchCondition = null;
    public JFunc1<WebElement, Boolean> getSearchCriteria() {
        return localSearchCondition != null
                ? localSearchCondition
                : SEARCH_CONDITION;
    }
    public BaseElement() {
        super.setEngine(new WebEngine(this));
    }
    public <T extends BaseElement> T setLocator(By locator) {
        engine().setLocator(locator);
        return (T) this;
    }
    public <T extends BaseElement> T init(By locator, Object parent) {
        setLocator(locator);
        setParent(parent);
        return (T)this;
    }

    public <T extends BaseElement> T initChildren(IBaseElement parent, IEngine engine) {
        new WebCascadeInit().initElements(this, engine.getDriverName());
        super.setEngine(engine);
        setParent(parent);
        return (T)this;
    }
    public <T extends BaseElement> T initChildren() {
        new WebCascadeInit().initElements(this, engine().getDriverName());
        return (T)this;
    }
    public <T extends BaseElement> T initChildren(IBaseElement parent) {
        return initChildren(parent, parent.engine());
    }
    public JavascriptExecutor jsExecutor() {
        return (JavascriptExecutor) getDriver();
    }
    public void jsExecute(String js, Object... args) {
        jsExecutor().executeScript(js, args);
    }
    public WebDriver getDriver() {
        return engine().getDriver();
    }
    public By getLocator() {
        return engine().getLocator();
    }

    @Override
    public WebEngine engine() {
        return (WebEngine) super.engine();
    }

    public BaseElement setEngine(WebEngine engine) {
        super.setEngine(engine.copy());
        return this;
    }
    public BaseElement setEngine(WebElement element) {
        super.setEngine(engine().copy(element));
        return this;
    }
    public BaseElement setEngine(By byLocator) {
        super.setEngine(engine().copy(byLocator));
        return this;
    }

    public BaseElement setEngine(By byLocator, WebEngine engine) {
        super.setEngine(engine.copy(byLocator));
        return this;
    }

    @Override
    public void setWaitTimeout(int sec) {
        logger.debug("Set wait timeout to " + sec);
        timeouts.setCurrentTimeoutSec(sec);
    }

    public String printContext() {
        By locator;
        BaseElement parent;
        String parentContext;
        return getParent() == null || !isClass(getParent().getClass(), BaseElement.class)
                || (locator = (parent = (BaseElement)getParent()).getLocator()) == null
            ? ""
            : isBlank(parentContext = parent.printContext())
                ? shortBy(locator)
                : parentContext + ">" + shortBy(locator);
    }
}