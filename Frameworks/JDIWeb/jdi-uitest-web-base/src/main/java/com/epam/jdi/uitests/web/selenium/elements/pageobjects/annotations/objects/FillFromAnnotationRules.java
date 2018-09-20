package com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.lang.reflect.Field;

import static com.epam.jdi.tools.ReflectionUtils.isInterface;

/**
 * Rules for filling form from annotation
 */
public class FillFromAnnotationRules {

    /**
     * Checks whether field has specified annotation
     * @param field field
     * @param annotationClass annotation class
     * @param interfaceClass interface class
     * @return true if field has specified annotation, otherwise false
     */
    public static boolean fieldHasAnnotation(Field field, Class annotationClass, Class interfaceClass) {
        boolean isAnnotation = field.isAnnotationPresent(annotationClass);
        return isAnnotation && isInterface(field, interfaceClass);
    }
}
