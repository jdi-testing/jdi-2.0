package com.epam.jdi.uitests.core.annotations;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.lang.reflect.Field;

import static com.epam.jdi.tools.StringUtils.splitCamelCase;

/**
 * Base annotation utilities
 */
public class AnnotationsUtil {
    
    /**
     * Protected constructor
     */
    protected AnnotationsUtil() {
    }
    
    /**
     * Gets a field name by annotation @Name, if the field or it's type is annotated with @Name,
     * otherwise gets a name of the field as a split camel case
     *
     * @param field a field of a class or an interface
     * @return field name
     */
    public static String getElementName(Field field) {
        if (field.isAnnotationPresent(Name.class))
            return field.getAnnotation(Name.class).value();
        if (field.getType().isAnnotationPresent(Name.class))
            return field.getType().getAnnotation(Name.class).value();
        return splitCamelCase(field.getName());
    }

}