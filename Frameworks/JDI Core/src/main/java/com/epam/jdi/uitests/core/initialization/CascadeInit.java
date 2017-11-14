package com.epam.jdi.uitests.core.initialization;
/* The MIT License
 *
 * Copyright 2004-2017 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */

/**
 * Created by Roman Iovlev on 10.03.2017
 */
import com.epam.jdi.uitests.core.interfaces.ISetup;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.base.IComposite;
import com.epam.jdi.uitests.core.templates.base.ETable;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;
import com.epam.jdi.uitests.core.interfaces.composite.IPage;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static com.epam.jdi.tools.LinqUtils.foreach;
import static com.epam.jdi.tools.ReflectionUtils.*;
import static com.epam.jdi.tools.StringUtils.LINE_BREAK;
import static com.epam.jdi.tools.TryCatchUtil.tryGetResult;
import static com.epam.jdi.uitests.core.initialization.MapInterfaceToElement.getClassFromInterface;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static java.lang.String.format;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.asList;

public abstract class CascadeInit {
    protected Class<?>[] decorators() { return new Class<?>[] {IBaseElement.class, List.class }; }

    // init non static fields
    public synchronized void initElements(Object parent, String driverName) {
        setFieldsForInit(parent, getFields(parent, decorators(), stopTypes()), parent.getClass(), driverName);
    }

    protected abstract Class<?>[] stopTypes();
    protected abstract IBaseElement fillInstance(IBaseElement instance, Field field);
    protected abstract IBaseElement getElementsRules(Field field, String driverName, Class<?> type, String fieldName) throws IllegalAccessException, InstantiationException;
    protected abstract <T> T getNewLocatorFromField(Field field);
    protected abstract IBaseElement initElements(Field field, Class<IBaseElement> genericClass);
    protected abstract void fillPageFromAnnotation(Field field, IBaseElement instance, Class<?> parentType);

    // Init Site as static class
    public synchronized void initStaticPages(Class<?> parentType, String driverName) {
        setFieldsForInit(null,
                getFields(asList(parentType.getDeclaredFields()), decorators(), f -> isStatic(f.getModifiers())),
                parentType, driverName);
    }
    private void setFieldsForInit(Object parent, List<Field> fields, Class<?> parentType, String driverName) {
        foreach(fields, field -> setElement(parent, parentType, field, driverName));
    }

    // Init site as instance
    public synchronized <T> T initPages(Class<T> site, String driverName) {
        T instance = tryGetResult(site::newInstance);
        initElements(instance, driverName);
        return instance;
    }

    private void setElement(Object parent, Class<?> parentType, Field field, String driverName) {
        try {
            Class<?> type = field.getType();
            IBaseElement instance = isInterface(type, IPage.class)
                    ? getInstancePage(parent, field, type, parentType)
                    : getInstanceElement(parent, type, parentType, field, driverName);
            instance.setName(field);
            if (parent != null)
                instance.setDriverName(driverName);
            instance.setTypeName(type.getSimpleName());
            field.set(parent, instance);
            if (isInterface(field, IComposite.class))
                initElements(instance, driverName);
        } catch (Exception ex) {
            throw exception("Error in setElement for field '%s' with parent '%s'", field.getName(),
                    parentType == null ? "NULL Class" : parentType.getSimpleName() + LINE_BREAK + ex.getMessage());
        }
    }

    private IBaseElement getInstancePage(Object parent, Field field, Class<?> type, Class<?> parentType) throws IllegalAccessException, InstantiationException {
        IBaseElement instance = (IBaseElement) getValueField(field, parent);
        if (instance == null)
            instance = (IBaseElement) type.newInstance();
        fillPageFromAnnotation(field, instance, parentType);
        return instance;
    }

    private IBaseElement getInstanceElement(Object parent, Class<?> type, Class<?> parentType, Field field, String driverName) {
        return createChildFromFieldStatic(parent, parentType, field, type, driverName);
    }

    protected IBaseElement specificAction(IBaseElement instance, Field field, Object parent, Class<?> type) {
        return instance;
    }
    protected IBaseElement fillFromJDIAnnotation(IBaseElement instance, Field field) {
        return instance;
    }
    private IBaseElement createChildFromFieldStatic(Object parent, Class<?> parentClass, Field field, Class<?> type, String driverName) {
        IBaseElement instance = (IBaseElement) getValueField(field, parent);
        if (instance == null)
            try {
                instance = getElementInstance(field, driverName, parent);
            } catch (Exception ex) {
                throw exception(
                        format("Can't create child for parent '%s' with type '%s'. Exception: %s",
                                parentClass.getSimpleName(), field.getType().getSimpleName(), ex.getMessage()));
            }
        else instance = fillInstance(instance, field);
        instance.setParent(parent);
        instance = fillFromJDIAnnotation(instance, field);
        instance = specificAction(instance, field, parent, type);
        return instance;
    }

    private IBaseElement getElementInstance(Field field, String driverName, Object parent) {
        Class<?> type = field.getType();
        String fieldName = field.getName();
        try { return getElementsRules(field, driverName, type, fieldName);
        } catch (Exception ex) {
            throw exception("Error in getElementInstance for field '%s'%s with type '%s'",
                    fieldName,
                    parent != null ? "in " + parent.getClass().getSimpleName() : "",
                    type.getSimpleName() + LINE_BREAK + ex.getMessage());
        }
    }
    protected <T> T getNewLocator(Field field) {
        try {
            return getNewLocatorFromField(field);
        } catch (Exception ex) {
            throw exception("Error in get locator for type '%s'", field.getType().getName()
                    + LINE_BREAK + ex.getMessage());
        }
    }
    protected IBaseElement getInstance(Field field, Class<?> type, String fieldName)
            throws IllegalAccessException, InstantiationException {
        IBaseElement instance = null;
        if (isClass(type, ETable.class)) {
            java.lang.reflect.Type[] types =((ParameterizedType) field.getGenericType())
                    .getActualTypeArguments();
            instance = new ETable((Class<?>) types[0], (Class<?>) types[1]);
        }
        if (instance == null && isInterface(type, List.class)) {
            Class<?> elementClass = (Class<?>) ((ParameterizedType) field.getGenericType())
                    .getActualTypeArguments()[0];
            if (elementClass.isInterface())
                elementClass = getClassFromInterface(type);
            if (elementClass != null && !isClass(elementClass, ITable.class) && elementClass.isAssignableFrom(IBaseElement.class))
                instance = initElements(field, (Class<IBaseElement>) elementClass);
        }
        if (instance == null) {
            if (type.isInterface())
                type = getClassFromInterface(type);
            instance = (IBaseElement) type.newInstance();
        }
        if (instance == null)
            throw exception(
                    "Unknown interface: %s (%s). Add relation interface -> class in VIElement.InterfaceTypeMap",
                    type, fieldName);
        return instance;
    }

    private static void fillFromAnnotation(IBaseElement instance, Field field) {
        try {
            ISetup setup = (ISetup) instance;
            setup.setup(field);
        } catch (Exception ignore) { }
    }
}