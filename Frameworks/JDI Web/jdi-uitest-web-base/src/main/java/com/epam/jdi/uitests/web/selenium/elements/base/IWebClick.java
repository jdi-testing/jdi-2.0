package com.epam.jdi.uitests.web.selenium.elements.base;
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


import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;
import org.openqa.selenium.interactions.Actions;

import static com.epam.jdi.uitests.web.selenium.elements.actions.WebStatic.*;


/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface IWebClick extends IClickable {
    @JDIAction("Click on element")
    default void clickJS() {
        js(this).executeScript("arguments[0].click();", webElement(this));
    }

    @JDIAction("Click on coordinates (x,y) = ({0}, {1})")
    default void clickByXY(int x, int y) {
        new Actions(driver(this)).moveToElement(webElement(this), x, y)
            .click().build().perform();
    }
}