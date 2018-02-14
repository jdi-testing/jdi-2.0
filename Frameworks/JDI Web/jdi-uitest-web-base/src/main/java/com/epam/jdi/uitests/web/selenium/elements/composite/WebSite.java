package com.epam.jdi.uitests.web.selenium.elements.composite;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.web.selenium.elements.WebCascadeInit;

import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.domain;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.getDriverFactory;

public class WebSite {
    public static Class currentSite;
    public static void init(Class<?> site) {
        init(site, getDriverFactory().currentDriverName());
    }
    public static void init(Class<?> site, String driverName) {
        WebCascadeInit.initSite(site, driverName);
    }

    public static void open(){
        WebPage site = new WebPage(domain);
        site.setName("Site Home page");
        site.open();
    }
    public static void shouldBeOpened(){
        if (!new WebPage(domain).url().check())
            open();
    }
}