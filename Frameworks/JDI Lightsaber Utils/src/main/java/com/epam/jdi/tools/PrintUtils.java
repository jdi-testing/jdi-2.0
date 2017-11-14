package com.epam.jdi.tools;
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
 * Created by Roman Iovlev on 10.27.2017
 */

import com.epam.jdi.tools.func.JFunc1;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.epam.jdi.tools.LinqUtils.*;
import static com.epam.jdi.tools.ReflectionUtils.getFields;
import static com.epam.jdi.tools.ReflectionUtils.getValueField;
import static java.lang.String.format;
import static java.util.Arrays.asList;

public final class PrintUtils {
    private PrintUtils() {
    }

    public static String print(Collection<String> list) {
        return print(list, ",", "%s");
    }

    public static String print(Collection<String> list, String separator) {
        return print(list, separator, "%s");
    }

    public static <T extends Enum> String printEnum(List<T> enums) {
        return enums != null ? String.join(",", select(enums, el -> format("%s", el))) : "";
    }

    public static String print(Collection<String> list, String separator, String format) {
        return list != null ? String.join(separator, select(list, el -> format(format, el))) : "";
    }

    public static String print(String[] list) {
        return print(list, ",", "%s");
    }

    public static String print(String[] list, String separator) {
        return print(list, separator, "%s");
    }

    public static String print(String[] list, String separator, String format) {
        return print(asList(list), separator, format);
    }
    public static <T> String print(Map<String, T> map, String separator, String format) {
        return print(toList(map, (k, v) -> MessageFormat.format(format, k, v)), separator, "%s");
    }
    public static <T> String print(Map<String, T> map, String separator) {
         return print(map, separator, "{0}:{1}");
    }
    public static <T> String print(Map<String, T> map) {
        return print(map, ";", "{0}:{1}");
    }

    public static String print(int[] list) {
        return print(list, ",", "%s");
    }

    public static String print(int[] list, String separator) {
        return print(list, separator, "%s");
    }

    public static String print(int[] list, String separator, String format) {
        List<String> result = new ArrayList<>();
        for (int i : list)
            result.add(Integer.toString(i));
        return print(result, separator, format);
    }

    public static String print(boolean[] list) {
        return print(list, ",", "%s");
    }

    public static String print(boolean[] list, String separator) {
        return print(list, separator, "%s");
    }

    public static String print(boolean[] list, String separator, String format) {
        List<String> result = new ArrayList<>();
        for (boolean i : list)
            result.add(Boolean.toString(i));
        return print(result, separator, format);
    }

    public static String printFields(Object obj) {
        return printFields(obj, ";");
    }

    public static String printFields(Object obj, String separator) {
        String className = obj.getClass().getSimpleName();
        String params = print(select(getFields(obj, String.class),
                field -> format("%s:%s", field.getName(), getValueField(field, obj))), separator, "%s");
        return format("%s(%s)", className, params);
    }
    public static <T> String print(Collection<T> list, JFunc1<T, String> func) {
        return print(map(list, func));
    }
    public static <T> String print(Collection<T> list, JFunc1<T, String> func, String separator) {
        return print(map(list, func), separator);
    }
    public static <T> String print(Collection<T> list,
           JFunc1<T, String> func, String separator, String format) {
        return print(map(list, func), separator, format);
    }
}