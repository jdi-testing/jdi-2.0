package com.epam.jdi.uitests.web.selenium.elements;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.uitests.core.exceptions.JDIUIException;
import com.epam.jdi.uitests.core.initialization.CascadeInit;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
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
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.getDriver;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.useDriver;
import static com.epam.jdi.uitests.web.selenium.driver.get.DriverData.DRIVER_NAME;
import static com.epam.jdi.uitests.web.selenium.elements.composite.WebSite.currentSite;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.*;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.*;

/**
 * Cascade initialization for Web
 */
public class WebCascadeInit extends CascadeInit {
    /**
     * Stop types
     * @return stop types
     */
    @Override
    protected Class<?>[] stopTypes() { return new Class<?>[] {Object.class, WebPage.class, Section.class, Element.class}; }

    /**
     * Decorators
     * @return decorators
     */
    @Override
    protected Class<?>[] decorators() { return new Class<?>[] {IBaseElement.class, List.class, WebElement.class }; }

    /**
     * Fills Page from JDI Annotation
     * @param field field
     * @param instance instance
     * @param parentType parentType
     */
    protected void fillPageFromAnnotation(Field field, IBaseElement instance, Class<?> parentType) {
        WebPage page = (WebPage) instance;
        if (field.getType().isAnnotationPresent(JPage.class))
            fillPageFromAnnotaiton(page, field.getType().getAnnotation(JPage.class), parentType);
        else {
            if (field.isAnnotationPresent(JPage.class))
                fillPageFromAnnotaiton(page, field.getAnnotation(JPage.class), parentType);
        }
        page.setName(field);
        WebPage.addPage(page);
    }

    /**
     * Fills Instance
     * @param instance instance
     * @param field field
     * @return IBaseElement
     */
    protected IBaseElement fillInstance(IBaseElement instance, Field field) {
        BaseElement element = (BaseElement) instance;
        if (!element.hasLocator())
            element.setEngine(new WebEngine(element, getNewLocator(field)));
        return element;
    }

    /**
     * Initializes Site
     * @param site site
     * @param driverName driverName
     */
    public static void initSite(Class<?> site, String driverName) {
        WebActions.Init();
        new WebCascadeInit().initStaticPages(site, driverName);
        currentSite = site;
    }

    /**
     * Initializes PageObject
     * @param clazz class
     * @param <T> type
     * @return page
     */
    public static <T> T initPageObject(Class<T> clazz) {
        return initPageObject(clazz, DRIVER_NAME);
    }

    /**
     * Initializes PageObject
     * @param clazz class
     * @param driver driver
     * @param <T> type
     * @return page
     */
    public static <T> T initPageObject(Class<T> clazz, JFunc<WebDriver> driver) {
        return initPageObject(clazz, useDriver(driver));
    }

    /**
     * Initializes PageObject
     * @param clazz class
     * @param driverName driverName
     * @param <T> type
     * @return page
     */
    public static <T> T initPageObject(Class<T> clazz, String driverName) {
        WebActions.Init();
        initFromProperties();
        T page;
        try {
            page = clazz.newInstance();
        } catch (Exception ignore) {
            try {
                page = clazz.getDeclaredConstructor(WebDriver.class).newInstance(getDriver());
            } catch (Exception ex) {
                throw new JDIUIException("Can't init PageObject: " + clazz.getName() + ". Exception: " + ex.getMessage());
            }
        }
        new WebCascadeInit().initElements(page, driverName);
        return page;
    }

    /**
     * Initializes PageObject
     * @param clazz class
     * @param <T> type
     */
    @SafeVarargs
    public static <T> void initPageObject(Class<T>... clazz) {
        initPageObject(DRIVER_NAME, clazz);
    }

    /**
     * Initializes PageObject
     * @param driver driver
     * @param clazz class
     * @param <T> type
     */
    @SafeVarargs
    public static <T> void initPageObject(WebDriver driver, Class<T>... clazz) {
        initDriver();
        initPageObject(useDriver(() -> driver), clazz);
    }

    /**
     * Initializes PageObject
     * @param driverName driverName
     * @param classes classes
     * @param <T> type
     */
    @SafeVarargs
    public static <T> void initPageObject(String driverName, Class<T>... classes) {
        for(Class<T> clazz : classes)
            initPageObject(clazz, driverName);
    }

    /**
     * Fills element from JDI Annotation
     * @param instance instance
     * @param field field
     * @return element
     */
    @Override
    protected IBaseElement fillFromJDIAnnotation(IBaseElement instance, Field field) {
        BaseElement element = (BaseElement) instance;
        fillFromAnnotation(element, field);
        return element;
    }

    /**
     * Specific action
     * @param instance instance
     * @param field field
     * @param parent parent
     * @param type type
     * @return element
     */
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

    /**
     * Gets locator from field
     * @param field field
     * @return By locator
     */
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