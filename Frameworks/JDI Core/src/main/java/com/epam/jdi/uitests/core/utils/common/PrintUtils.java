package com.epam.jdi.uitests.core.utils.common;
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
import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.core.annotations.AnnotationsUtil;

import java.util.ArrayList;
import java.util.List;

import static com.epam.jdi.tools.EnumUtils.getEnumValue;
import static com.epam.jdi.tools.PrintUtils.print;
import static com.epam.jdi.tools.ReflectionUtils.*;
import static java.lang.reflect.Array.get;
import static java.lang.reflect.Array.getLength;

public final class PrintUtils {
    private PrintUtils() {
    }
    public static MapArray<String, String> getMapFromObject(Object obj) {
        if (obj == null)
            return new MapArray<>();
        return new MapArray<>(getFields(obj, Object.class),
            AnnotationsUtil::getElementName,
            field -> {
                Object value = getValueField(field, obj);
                if (value == null)
                    return null;
                if (isClass(value.getClass(), String.class, Integer.class, Boolean.class))
                    return value.toString();
                if (isClass(value.getClass(), Enum.class))
                    return getEnumValue((Enum) value);
                return null;
                // TODO
                /*if (field.isAnnotationPresent(Complex.class))
                    printObject(value)*/
            });
    }

    public static String printObjectAsArray(Object array) {
        List<String> elements = new ArrayList<>();
        for (int i = 0; i <= getLength(array); i++)
            elements.add(get(array, i).toString());
        return print(elements);
    }
}