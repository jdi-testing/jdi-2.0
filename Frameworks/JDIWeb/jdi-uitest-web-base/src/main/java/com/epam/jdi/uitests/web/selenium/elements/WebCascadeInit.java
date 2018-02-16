package com.epam.jdi.uitests.web.selenium.elements;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.uitests.core.initialization.CascadeInit;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.web.selenium.driver.get.driver.DriverTypes;
import com.epam.jdi.uitests.web.selenium.elements.actions.WebActions;
import com.epam.jdi.uitests.web.selenium.elements.apiInteract.WebEngine;
import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.Frame;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi.tools.LinqUtils.any;
import static com.epam.jdi.tools.LinqUtils.first;
import static com.epam.jdi.uitests.core.settings.JDIData.APP_VERSION;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.currentDriverName;
import static com.epam.jdi.uitests.web.selenium.elements.composite.WebSite.currentSite;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.*;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.*;

public class WebCascadeInit extends CascadeInit {
    @Override
    protected Class<?>[] stopTypes() { return new Class<?>[] {Object.class, WebPage.class, Section.class, Element.class}; }
    @Override
    protected Class<?>[] decorators() { return new Class<?>[] {IBaseElement.class, List.class, WebElement.class }; }

    protected void fillPageFromAnnotation(Field field, IBaseElement instance, Class<?> parentType) {
        if (field.getType().isAnnotationPresent(JPage.class))
            fillPageFromAnnotaiton((WebPage) instance, field.getType().getAnnotation(JPage.class), parentType);
        else {
            if (field.isAnnotationPresent(JPage.class))
                fillPageFromAnnotaiton((WebPage) instance, field.getAnnotation(JPage.class), parentType);
        }
    }
    protected IBaseElement fillInstance(IBaseElement instance, Field field) {
        BaseElement element = (BaseElement) instance;
        if (!element.hasLocator())
            element.setEngine(new WebEngine(element, getNewLocator(field)));
        return element;
    }

    public static void initSite(Class<?> site, String driverName) {
        WebActions.Init();
        new WebCascadeInit().initStaticPages(site, driverName);
        currentSite = site;
    }
    public static <T> T initPageObject(Class<T> clazz) {
        return initPageObject(clazz, currentDriverName);
    }
    public static <T> T initPageObject(Class<T> clazz, JFunc<WebDriver> driver) {
        return initPageObject(clazz, useDriver(driver));
    }
    public static <T> T initPageObject(Class<T> clazz, DriverTypes driver){
        return initPageObject(clazz, useDriver(driver));
    }
    public static <T> T initPageObject(Class<T> clazz, String driverName) {
        WebActions.Init();
        T page;
        try {
            page = clazz.newInstance();
        } catch (Exception ignore) {
            try {
                page = clazz.getDeclaredConstructor(WebDriver.class).newInstance(getDriver());
            } catch (Exception ex) {
                throw new RuntimeException("Can't init PageObject: " + clazz.getName() + ". Exception: " + ex.getMessage());
            }
        }
        new WebCascadeInit().initElements(page, driverName);
        return page;
    }

    @SafeVarargs
    public static <T> void initPageObject(Class<T>... clazz) {
        initPageObject(currentDriverName, clazz);
    }
    @SafeVarargs
    public static <T> void initPageObject(WebDriver driver, Class<T>... clazz) {
        initDriver();
        initPageObject(useDriver(() -> driver), clazz);
    }
    @SafeVarargs
    public static <T> void initPageObject(DriverTypes driver, Class<T>... clazz){
        initDriver();
        initPageObject(useDriver(driver), clazz);
    }
    @SafeVarargs
    public static <T> void initPageObject(String driverName, Class<T>... classes) {
        for(Class<T> clazz : classes)
            initPageObject(clazz, driverName);
    }
    @Override
    protected IBaseElement fillFromJDIAnnotation(IBaseElement instance, Field field) {
        BaseElement element = (BaseElement) instance;
        fillFromAnnotation(element, field);
        return element;
    }
    @Override
    protected IBaseElement specificAction(IBaseElement instance, Field field, Object parent, Class<?> type) {
        BaseElement element = (BaseElement) instance;
        By locator = getNewLocator(field);
        locator = locator != null ? locator : element.getLocator();
        element.setEngine(element.engine() != null && locator == null
            ? new WebEngine(element)
            : new WebEngine(element, locator));
        if (parent != null && type == null)
            return element;
        By frameBy = getFrame(field.getDeclaredAnnotation(Frame.class));
        if (frameBy != null)
            element.engine().setFrame(frameBy);
        return element;
    }

    protected By getNewLocatorFromField(Field field) {
        FindBy[] jfindbys = field.getAnnotationsByType(FindBy.class);
        if (jfindbys.length > 0 && any(jfindbys, j -> APP_VERSION.equals(j.group())))
            return findByToBy(first(jfindbys, j -> APP_VERSION.equals(j.group())));
        if (field.isAnnotationPresent(FindBy.class))
            return findByToBy(field.getAnnotation(FindBy.class));
        if (field.isAnnotationPresent(org.openqa.selenium.support.FindBy.class))
            return findByToBy(field.getAnnotation(org.openqa.selenium.support.FindBy.class));
        if (field.isAnnotationPresent(Css.class))
            return findByToBy(field.getAnnotation(Css.class));
        if (field.isAnnotationPresent(XPath.class))
            return findByToBy(field.getAnnotation(XPath.class));
        if (field.isAnnotationPresent(ByText.class))
            return findByToBy(field.getAnnotation(ByText.class));
        if (field.isAnnotationPresent(WithText.class))
            return findByToBy(field.getAnnotation(WithText.class));
        if (field.isAnnotationPresent(ById.class))
            return findByToBy(field.getAnnotation(ById.class));
        return null;
    }

}