package com.epam.jdi.uitests.web.selenium.elements.actions;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Static methods for WebElements
 */
public class WebStatic {
    /**
     * Returns base element
     * @param o object
     * @return BaseElement
     */
    static BaseElement base(Object o) { return ((BaseElement)o); }

    /**
     * Returns element
     * @param o object
     * @return Element
     */
    public static Element element(Object o) { return (Element) o; }

    /**
     * Returns WebElement
     * @param o object
     * @param args arguments
     * @return WebElement
     */
    public static WebElement webElement(Object o, Object... args) {
        return base(o).engine().getWebElement(args); }

    /**
     * Returns WebElements
     * @param o object
     * @return WebElements
     */
    public static List<WebElement> webElements(Object o) { return base(o).engine().findElements(); }

    /**
     * Returns driver
     * @param o object
     * @return WebDriver
     */
    public static WebDriver driver(Object o) { return base(o).getDriver(); }

    /**
     * Returns JavascriptExecutor
     * @param o object
     * @return JavascriptExecutor
     */
    public static JavascriptExecutor js(Object o) { return (JavascriptExecutor)driver(o); }

    /**
     * Returns attribute
     * @param o object
     * @param name attribute name
     * @return attribute value
     */
    public static String attribute(Object o, String name) { return webElement(o).getAttribute(name); }

    /**
     * Return actions
     * @param o object
     * @return Actions
     */
    public static Actions actions(Object o) { return new Actions(driver(o)); }

    /**
     * Returns WebPage
     * @param o object
     * @return WebPage
     */
    public static WebPage page(Object o) { return (WebPage)o; }

    /**
     * Returns isSelected
     * @param el element
     * @return true if element is selected, otherwise false
     */
    public static boolean isSelected(WebElement el) { return el.isSelected() ||
            el.getAttribute("checked")!=null; }

    /**
     * Returns WebElement
     * @param o object
     * @return WebElement
     */
    public static WebElement we(Object o) { return (WebElement)o; }
}
