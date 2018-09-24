package com.epam.jdi.uitests.web.selenium.elements.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.codeborne.selenide.Condition;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.base.IElement;
import com.epam.jdi.uitests.core.settings.JDISettings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Arrays;
import java.util.List;

import static com.epam.jdi.tools.ReflectionUtils.newEntity;
import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;
import static com.epam.jdi.uitests.core.settings.JDISettings.asserter;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static java.lang.String.format;

/**
 * Element
 */
public class Element extends BaseElement implements IElement, IHasElement {

    /**
     * Gets element
     * @return WebElement
     */
    @JDIAction(level = DEBUG)
    public WebElement getWebElement() {
        return getElement();
    }

    /**
     * Gets element without timeout
     * @return WebElement
     */
    @JDIAction(level = DEBUG)
    public WebElement getNowWebElement() {
        setWaitTimeout(0);
        WebElement el = null;
        try {
            el = getDriver().findElement(getLocator());
        } catch (Exception ignore) {}
        restoreWaitTimeout();
        return el;
    }

    /**
     * Returns first element
     * @return WebElement
     */
    @JDIAction(level = DEBUG)
    public WebElement firstWebElement() {
        List<WebElement> els = engine().findElements();
        if (els.size() > 0)
            return els.get(0);
        else
            throw exception("Can't find element " + toString());
    }

    /**
     * Sets element
     * @param webElement element
     * @param <T> type
     * @return Element
     */
    @JDIAction(level = DEBUG)
    public <T extends Element> T setWebElement(WebElement webElement) {
        engine().setWebElement(webElement);
        return (T) this;
    }

    /**
     * Finds element by locator
     * @param locator locator
     * @return WebElement
     */
    @JDIAction(level = DEBUG)
    public WebElement findElement(By locator) {
        return getWebElement().findElement(locator);
    }

    /**
     * Finds elements by locator
     * @param locator locator
     * @return list of WebElements
     */
    @JDIAction(level = DEBUG)
    public List<WebElement> findElements(By locator) {
        return getWebElement().findElements(locator);
    }

    /**
     * Gets invisible element
     * @return WebElement
     */
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

    /**
     * Selects area
     * @param x1 X1
     * @param y1 Y1
     * @param x2 X2
     * @param y2 Y2
     */
    @JDIAction
    public void selectArea(int x1, int y1, int x2, int y2) {
        WebElement element = getWebElement();
        new Actions(getDriver()).moveToElement(element, x1, y1).clickAndHold()
                .moveToElement(element, x2, y2).release().build().perform();
    }

    /**
     * Drags and drops by
     * @param x X
     * @param y Y
     */
    @JDIAction
    public void dragAndDropBy(int x, int y) {
        new Actions(getDriver()).dragAndDropBy(getWebElement(), x, y).build().perform();
    }

    /**
     * Drags and drops to target
     * @param target target
     */
    @JDIAction
    public void dragAndDropTo(Element target) {
        new Actions(getDriver()).dragAndDrop(getWebElement(), target.getWebElement()).build().perform();
    }

    /**
     * Hovers over
     */
    @JDIAction
    public void hover() {
        Actions action = new Actions(getDriver());
        action.moveToElement(getWebElement()) .clickAndHold().build().perform();
    }

    /**
     * Copies element
     * @param newLocator new locator
     * @param <T> type
     * @return Element
     */
    public <T extends Element> T copy(By newLocator) {
        try {
            T result = newEntity((Class<T>) getClass());
            result.setEngine(newLocator, engine());
            return result;
        } catch (Exception ex) {
            throw JDISettings.exception("Can't copy Element: " + this);
        }
    }

    /**
     * Gets element by locator
     * @param locator locator
     * @return WebElement
     */
    public WebElement get(By locator) {
        Element el = new Element().setLocator(locator).setParent(this);
        return el.getWebElement();
    }

    /**
     * Highlights element
     * @param color color
     * @return Element
     */
    @JDIAction
    public Element higlight(String color) {
        jsExecute("style.border='3px dashed "+color+"'");
        return this;
    }

    /**
     * Highlights element with red
     * @return Element
     */
    public Element higlight() {
        show();
        return higlight("red");
    }

    /**
     * Shows element
     * @return Element
     */
    @JDIAction
    public Element show() {
        jsExecute("scrollIntoView(true)");
        return this;
    }

    /**
     * Drags and drops to
     * @param to element
     * @return Element
     */
    //region Actions
    @JDIAction
    public Element dragAndDropTo(WebElement to) {
        doActions(a -> a.clickAndHold(getElement()).moveToElement(to).release(to));
        return this;
    }

    /**
     * Double clicks element
     * @return Element
     */
    @JDIAction
    public Element doubleClick() {
        doActions(Actions::doubleClick);
        return this;
    }

    /**
     * Right clicks element
     * @return Element
     */
    @JDIAction
    public Element rightClick() {
        doActions(Actions::contextClick);
        return this;
    }

    /**
     * Drags and drops to
     * @param x X
     * @param y Y
     * @return Element
     */
    @JDIAction
    public Element dragAndDropTo(int x, int y) {
        doActions(a -> a.dragAndDropBy(getElement(), x, y));
        return this;
    }

    /**
     * Performs actions
     * @param actions actions
     * @return Element
     */
    public Element doActions(JFunc1<Actions, Actions> actions) {
        actions.execute(new Actions(getDriver())).build().perform();
        return this;
    }
    //endregion


    /**
     * Asserts conditions
     * @param conditions conditions
     * @return Element
     */
    public IBaseElement should(Condition... conditions){
        Arrays.stream(conditions).forEach(condition ->
                asserter.isTrue(condition.apply(getWebElement()),
                        format("Expected: '%s' but found '%s'", condition.toString(), getWebElement().getText())
                )
        );
        return this;
    }

    /**
     * Asserts conditions
     * @param conditions conditions
     * @return Element
     */
    public IBaseElement shouldHave(Condition... conditions){
        return should(conditions);
    }

    /**
     * Asserts conditions
     * @param conditions conditions
     * @return Element
     */
    public IBaseElement shouldBe(Condition... conditions){
        return should(conditions);
    }

    /**
     * Asserts negative conditions
     * @param conditions conditions
     * @return Element
     */
    public IBaseElement shouldNot(Condition... conditions){
        Arrays.stream(conditions).forEach(condition ->
                asserter.isTrue(!condition.apply(getWebElement()),
                        format("Expected: '%s' but found '%s'", condition.toString(), getWebElement().getText())
                )
        );
        return this;
    }

    /**
     * Asserts negative conditions
     * @param conditions conditions
     * @return Element
     */
    public IBaseElement shouldNotHave(Condition... conditions){
        return shouldNot(conditions);
    }

    /**
     * Asserts negative conditions
     * @param conditions conditions
     * @return Element
     */
    public IBaseElement shouldNotBe(Condition... conditions){
        return shouldNot(conditions);
    }

}