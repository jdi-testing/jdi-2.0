package com.epam.jdi.uitests.core.initialization;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.map.MapArray;

import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

public final class MapInterfaceToElement {
    private MapInterfaceToElement() {
    }

    private static MapArray<Class, Class> map = new MapArray<>();

    public static void init(Object[][] pairs) {
        map = new MapArray<>(pairs);
    }

    public static void update(Object[][] pairs) {
        map.addOrReplace(pairs);
    }

    /**
     * Get class from interface
     *
     * @param clazz
     * @param fieldName
     * @return Class
     */
    public static Class getClassFromInterface(Class clazz, String fieldName) {
        if (map.keys().contains(clazz))
            return map.get(clazz);
        else throw noInterfaceException(clazz, fieldName);
    }

    /**
     * noInterfaceException
     *
     * @param type
     * @param fieldName
     */
    public static AssertionError noInterfaceException(Class<?> type, String fieldName) {
        return exception(
                "Unknown interface: %s (%s). Add relation interface -> class using MapInterfaceToElement.update",
                type, fieldName);
    }
}