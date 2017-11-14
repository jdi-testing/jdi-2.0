package com.epam.web.matcher.base;

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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jdi.tools.PrintUtils.print;
import static com.epam.jdi.tools.ReflectionUtils.*;
import static java.lang.Integer.parseInt;
import static java.lang.reflect.Array.get;
import static java.lang.reflect.Array.getLength;

public final class PrintUtils {

    private PrintUtils() {
    }

    private static String printObject(Object obj) {
        List<String> result = new ArrayList<>();
        for (Field field : getFields(obj, Object.class)) {
            Object value = getValueField(field, obj);
            String strValue = null;
            if (value == null)
                strValue = "#NULL#";
            else if (isClass(value.getClass(), String.class))
                strValue = (String) value;
            else if (isClass(value.getClass(), Enum.class))
                strValue = value.toString();
            else if (field.isAnnotationPresent(Complex.class))
                strValue = "#(#" + printObject(value) + "#)#";
            if (strValue != null)
                result.add(String.format("%s#:#%s", field.getClass().getSimpleName(), strValue));
        }
        return print(result, "#;#", "%s");
    }

    public static MapArray<String, String> objToSetValue(Object obj) {
        return (obj == null)
                ? new MapArray<>()
                : parseObjectAsString(printObject(obj));
    }

    public static String processValue(String input, List<String> values) {
        if (input.equals("#NULL#"))
            return null;
        if (input.matches("#VAL\\d*"))
            return values.get(parseInt(input.substring(4)) - 1);
        return input;
    }

    public static MapArray<String, String> parseObjectAsString(String string) {
        if (string == null)
            return null;
        MapArray<String, String> result = new MapArray<>();
        List<String> values = new ArrayList<>();
        int i = 1;
        String str = string;
        while (string.indexOf("#(#") > 0) {
            values.add(string.substring(string.indexOf("#(#") + 3, string.indexOf("#)#")));
            str = string.replaceAll("#\\(#.*#\\)#", "#VAL" + i++);
        }
        String[] fields = str.split("#;#");
        for (String field : fields) {
            String[] splitField = field.split("#:#");
            if (splitField.length == 2)
                result.add(splitField[0], processValue(splitField[1], values));
        }
        return result;
    }

    public static String printObjectAsArray(Object array) {
        List<String> elements = new ArrayList<>();
        for (int i = 0; i <= getLength(array); i++)
            elements.add(get(array, i).toString());
        return print(elements);
    }
}
