package com.epam.jdi.uitests.web.selenium.elements.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import org.openqa.selenium.*;

public class J extends Element implements WebElement {
    /**
     * Clicks element
     */
    public void click() {
        getWebElement().click();
    }

    /**
     * Submits element
     */
    public void submit() {
        getWebElement().submit();
    }

    /**
     * Sends keys to element
     * @param charSequences
     */
    public void sendKeys(CharSequence... charSequences) {
        getWebElement().sendKeys(charSequences);
    }

    /**
     * Clears element
     */
    public void clear() {
        getWebElement().clear();
    }

    /**
     * Gets element's tag name
     * @return tag name
     */
    public String getTagName() {
        return getWebElement().getTagName();
    }

    /**
     * Checks whether element is selected
     * @return true is element is selected, otherwise false
     */
    public boolean isSelected() {
        return getWebElement().isSelected();
    }

    /**
     * Checks whether element is enabled
     * @return true if element is enabled, otherwise false
     */
    public boolean isEnabled() {
        return enabled();
    }

    /**
     * Gets text of element
     * @return text
     */
    public String getText() {
        return getWebElement().getText();
    }

    /**
     * Checks whether element is displayed
     * @return true if element is displayed, otherwise false
     */
    public boolean isDisplayed() {
        return displayed();
    }

    /**
     * Gets element's location
     * @return location
     */
    public Point getLocation() {
        return getWebElement().getLocation();
    }

    /**
     * Gets element's size
     * @return size
     */
    public Dimension getSize() {
        return getWebElement().getSize();
    }

    /**
     * Gets element's rect
     * @return rect
     */
    public Rectangle getRect() {
        return getWebElement().getRect();
    }

    /**
     * Gets css value
     * @param s string
     * @return
     */
    public String getCssValue(String s) {
        return getWebElement().getCssValue(s);
    }

    /**
     * Gets screenshot as type
     * @param outputType type
     * @param <X> type
     * @return type
     * @throws WebDriverException
     */
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return getWebElement().getScreenshotAs(outputType);
    }

    /**
     * Gets attribute
     * @param name Specify name for attribute
     * @return attribute
     */
    public String getAttribute(String name) {
        return super.getAttribute(name);
    }
}
