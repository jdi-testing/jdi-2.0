package com.epam.jdi.uitests.web.selenium.elements.base;

import java.util.List;

/**
 * Created by Roman_Iovlev on 8/24/2017.
 */
public class J extends Element implements WebElement {
    public void click() {
        getWebElement().click();
    }
    public void submit() {
        getWebElement().submit();
    }
    public void sendKeys(CharSequence... charSequences) {
        getWebElement().sendKeys(charSequences);
    }
    public void clear() {
        getWebElement().clear();
    }
    public String getTagName() {
        return getWebElement().getTagName();
    }
    public String getAttribute(String s) {
        return getWebElement().getAttribute(s);
    }
    public boolean isSelected() {
        return getWebElement().isSelected();
    }
    public boolean isEnabled() {
        return getWebElement().isEnabled();
    }
    public String getText() {
        return getWebElement().getText();
    }
    public List<WebElement> findElements(By by) {
        return getWebElement().findElements(by);
    }
    public WebElement findElement(By by) {
        return getWebElement().findElement(by);
    }
    public boolean isDisplayed() {
        return getWebElement().isDisplayed();
    }
    public Point getLocation() {
        return getWebElement().getLocation();
    }
    public Dimension getSize() {
        return getWebElement().getSize();
    }
    public Rectangle getRect() {
        return getWebElement().getRect();
    }
    public String getCssValue(String s) {
        return getWebElement().getCssValue(s);
    }
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return getWebElement().getScreenshotAs(outputType);
    }
}
