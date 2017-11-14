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


import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.core.annotations.Title;
import com.epam.jdi.uitests.core.interfaces.common.IText;
import com.epam.jdi.uitests.core.interfaces.complex.IList;
import com.epam.jdi.uitests.web.selenium.elements.WebCascadeInit;
import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.Text;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi.tools.EnumUtils.getEnumValue;
import static com.epam.jdi.tools.ReflectionUtils.getValueField;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Elements<T extends BaseElement> extends BaseElement implements IList<T> {
    private MapArray<String, T> elements;
    private Class<T> classType;
    public String titleFieldName = null;
    public static final String NO_TITLE_FIELD = "NO TITLE FIELD";

    public Elements(By byLocator, Class<T> classType) {
        setLocator(byLocator);
        this.classType = classType != null ? classType : (Class<T>) Button.class;
        elements = new MapArray<>();
        setUseCache(false);
    }
    private List<WebElement> getWebElements() {
        return getElements();
    }
    public MapArray<String, T> getAll() {
        if (isUseCache())
            if (!elements.isEmpty() && elements.get(0).value.displayed())
                return elements;
        else elements.clear();
        return elements = new MapArray<>(getWebElements(),
                this::elementTitle,
                this::initElement);
    }
    private String elementTitle(WebElement el) {
        if (titleFieldName == null)
            identifyTitleField();
        return titleFieldName.equals(NO_TITLE_FIELD)
                ? el.getText()
                : getElementTitle(el, titleFieldName);
    }
    private String getElementTitle(WebElement el, String titleField) {
        T element = initElement(el);
        Field field = null;
        try { field = element.getClass().getField(titleField);
        } catch (NoSuchFieldException ex) { /* if titleField defined then field always exist */ }
        return ((IText) getValueField(field, element)).getText();
    }

    private T initElement(WebElement el) {
        try {
            T element = classType.newInstance();
            element.setEngine(el);
            element.setParent(null);
            // TODO remove or implement element useCache = useCache;
            new WebCascadeInit().initElements(element, engine().getDriverName());
            return element;
        } catch (Exception ex) {
            throw exception("Can't instantiate list element");
        }
    }

    public <E> List<E> asData(Class<E> entityClass) {
        return map((k, v) -> v.asEntity(entityClass));
    }
    private boolean isTextElement(Field field) {
        return field.getType().equals(Text.class) || field.getType().equals(IText.class);
    }
    public T get(String name) {
        return getAll().get(name);
    }
    private void identifyTitleField() {
        Field titleField = null;
        boolean textFieldIsTitle = true;
        Field[] fields = classType.getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Title.class)) {
                titleFieldName = field.getName();
                return;
            }
            if (textFieldIsTitle && isTextElement(field)) {
                if (titleField == null)
                    titleField = field;
                else
                    textFieldIsTitle = false;
            }
        }
        if (titleField != null && textFieldIsTitle)
            titleFieldName = titleField.getName();
        titleFieldName = NO_TITLE_FIELD;
    }

    public T get(Enum name) {
        return get(getEnumValue(name));
    }

}