package com.epam.jdi.uitests.core.actions.base;

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

import com.epam.jdi.uitests.core.interfaces.common.IButton;
import com.epam.jdi.uitests.core.interfaces.common.IText;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.epam.jdi.tools.LinqUtils.first;
import static com.epam.jdi.tools.LinqUtils.select;
import static com.epam.jdi.tools.ReflectionUtils.getFields;
import static com.epam.jdi.tools.ReflectionUtils.getValueField;
import static com.epam.jdi.tools.StringUtils.namesEqual;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

public class UIUtils {
    public static IButton getButton(Object obj, String buttonName) {
        List<Field> fields = getFields(obj, IButton.class);
        switch (fields.size()) {
            case 0:
                throw exception("Can't find any buttons on form '%s.", obj);
            case 1:
                return (IButton) getValueField(fields.get(0), obj);
            default:
                Collection<IButton> buttons = select(fields, f -> (IButton) getValueField(f, obj));
                IButton button = first(buttons, b -> namesEqual(toButton(b.getName()), toButton(buttonName)));
                if (button == null)
                    throw exception("Can't find button '%s' for Element '%s'", buttonName, obj);
                return button;
        }
    }

    private static String toButton(String buttonName) {
        return buttonName.toLowerCase().contains("button") ? buttonName : buttonName + "button";
    }

    public static IButton getButton(Object obj, Functions funcName) {
        List<Field> fields = getFields(obj, IButton.class);
        if (fields.size() == 1)
            return (IButton) getValueField(fields.get(0), obj);
        Collection<IButton> buttons = select(fields, f -> (IButton) getValueField(f, obj));
        String name = funcName.name + "button";
        IButton button = first(buttons, b -> {
            String bName = b.getName().toLowerCase();
            bName = bName.toLowerCase().contains("button") ? bName : bName + "button";
            return namesEqual(bName, name);
        });
        if (button == null)
            throw exception("Can't find button '%s' for Element '%s'", name, obj);
        return button;
    }

    public static IText getTextElement(Object obj) {
        Field textField = first(obj.getClass().getDeclaredFields(),  f ->
            f.getType() == IText.class || f.getType() == IText.class);
        if (textField == null)
            throw exception("Can't find Text Element '%s'", obj);
        return (IText) getValueField(textField, obj);
    }

}
