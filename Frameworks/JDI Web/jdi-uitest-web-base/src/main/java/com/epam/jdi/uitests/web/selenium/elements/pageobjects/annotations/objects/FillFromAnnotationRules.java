package com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects;

import java.lang.reflect.Field;

import static com.epam.jdi.tools.ReflectionUtils.isInterface;

/**
 * Created by 12345 on 17.05.2016.
 */
public class FillFromAnnotationRules {

    public static boolean fieldHasAnnotation(Field field, Class annotationClass, Class interfaceClass) {
        boolean isAnnotation = field.isAnnotationPresent(annotationClass);
        return isAnnotation && isInterface(field, interfaceClass);
    }
}
