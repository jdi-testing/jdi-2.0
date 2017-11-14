package com.epam.jdi.uitests.web.selenium.elements.complex;
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


import com.epam.jdi.uitests.core.interfaces.ISetup;
import com.epam.jdi.uitests.core.interfaces.complex.IDropList;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropList;

import java.lang.reflect.Field;

import static com.epam.jdi.uitests.web.selenium.elements.base.LinkedSetup.setupDropElement;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;

/**
 * Select control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class DropList<TEnum extends Enum> extends Dropdown<TEnum>
        implements IDropList<TEnum>, ISetup {

    public void setUp(Field field) {
        if (!fieldHasAnnotation(field, JDropList.class, IDropList.class))
            return;
        JDropList jDroplist = field.getAnnotation(JDropList.class);
        setupDropElement(this, findByToBy(jDroplist.root()),
                findByToBy(jDroplist.value()),
                findByToBy(jDroplist.list()),
                findByToBy(jDroplist.expand()));
    }

    private String separator = ", ";
    public String getSeparator() { return separator; }
    public DropList<TEnum> setValuesSeparator(String separator) {
        this.separator = separator;
        return this;
    }
}