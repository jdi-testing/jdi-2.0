package com.epam.jdi.uitests.core.utils.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
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