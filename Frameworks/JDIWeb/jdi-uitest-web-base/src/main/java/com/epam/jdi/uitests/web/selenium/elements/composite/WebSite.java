package com.epam.jdi.uitests.web.selenium.elements.composite;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.web.selenium.elements.WebCascadeInit;

import static com.epam.jdi.uitests.web.selenium.driver.get.DriverData.DRIVER_NAME;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.domain;

/**
 * WebSite composite element
 */
public class WebSite {
    public static Class currentSite;

    /**
     * Initializes site
     * @param site site
     */
    public static void init(Class<?> site) {
        init(site, DRIVER_NAME);
    }

    /**
     * Initialies site
     * @param site site
     * @param driverName driverName
     */
    public static void init(Class<?> site, String driverName) {
        WebCascadeInit.initSite(site, driverName);
    }

    /**
     * Openes site
     */
    public static void open(){
        WebPage site = new WebPage(domain);
        site.setName("Site Home page");
        site.open();
    }

    /**
     * Openes site
     */
    public static void shouldBeOpened(){
        if (!new WebPage(domain).url().check())
            open();
    }
}