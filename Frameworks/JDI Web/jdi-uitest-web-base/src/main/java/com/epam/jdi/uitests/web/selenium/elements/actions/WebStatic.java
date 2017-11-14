package com.epam.jdi.uitests.web.selenium.elements.actions;

import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Created by Roman_Iovlev on 9/22/2017.
 */
public class WebStatic {
    static BaseElement base(Object o) { return ((BaseElement)o); }
    public static Element element(Object o) { return (Element) o; }
    public static WebElement webElement(Object o, Object... args) {
        return base(o).engine().getWebElement(args); }
    public static List<WebElement> webElements(Object o, Object... args) { return base(o).engine().findElements(); }
    public static WebDriver driver(Object o) { return base(o).getDriver(); }
    public static JavascriptExecutor js(Object o) { return (JavascriptExecutor)driver(o); }
    public static String attribute(Object o, String name) { return webElement(o).getAttribute(name); }
    public static Actions actions(Object o) { return new Actions(driver(o)); }
    public static WebPage page(Object o) { return (WebPage)o; }
    public static boolean isSelected(WebElement el) { return el.isSelected() ||
            el.getAttribute("checked")!=null; }

    public static WebElement we(Object o) { return (WebElement)o; }
}
