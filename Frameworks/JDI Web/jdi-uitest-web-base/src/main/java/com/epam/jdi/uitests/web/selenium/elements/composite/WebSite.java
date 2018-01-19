package com.epam.jdi.uitests.web.selenium.elements.composite;
/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */


import com.epam.jdi.uitests.web.selenium.elements.WebCascadeInit;

import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.domain;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.getDriverFactory;

/**
 * Created by Roman_Iovlev on 8/30/2015.
 */
public class WebSite {
    public static Class currentSite;
    public static void init(Class<?> site) {
        init(site, getDriverFactory().currentDriverName());
    }
    public static void init(Class<?> site, String driverName) {
        WebCascadeInit.initSite(site, driverName);
    }

    public static void open(){
        new WebPage(domain).open();
    }
    public static void shouldBeOpened(){
        if (!new WebPage(domain).url().check())
            open();
    }
}