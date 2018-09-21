package com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.complex.tables.CheckTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface JPage {
    /**
     * Returns value
     * @return value
     */
    String value() default "";

    /**
     * Returns URL
     * @return URL
     */
    String url() default "";

    /**
     * Returns title
     * @return title
     */
    String title() default "";

    /**
     * Returns url template
     * @return url template
     */
    String urlTemplate() default "";

    /**
     * Returns url check type
     * @return url chack type
     */
    CheckTypes urlCheckType() default CheckTypes.EQUAL;

    /**
     * Returns title check type
     * @return title check type
     */
    CheckTypes titleCheckType() default CheckTypes.EQUAL;
}