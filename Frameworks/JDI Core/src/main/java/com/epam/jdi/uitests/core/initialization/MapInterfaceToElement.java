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
import com.epam.jdi.tools.map.MapArray;

import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

public final class MapInterfaceToElement {
    private MapInterfaceToElement() { }
    private static MapArray<Class, Class> map = new MapArray<>();

    public static void init(Object[][] pairs) {
        map = new MapArray<>(pairs);
    }
    public static void update(Object[][] pairs) {
        map.addOrReplace(pairs);
    }

    public static Class getClassFromInterface(Class clazz, String fieldName) {
        if (map.keys().contains(clazz))
            return map.get(clazz);
        else throw noInterfaceException(clazz, fieldName);
    }
    public static RuntimeException noInterfaceException(Class<?> type, String fieldName) {
        return exception(
                "Unknown interface: %s (%s). Add relation interface -> class using MapInterfaceToElement.update",
                type, fieldName);
    }
}