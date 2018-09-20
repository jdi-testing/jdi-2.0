package com.epam.jdi.uitests.core.annotations;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.lang.annotation.*;

/**
 * {@code @Print} allows to print a method name and its parameters. It is used in {@code PrintProcessor}
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Print {
    /**
     * If a non default value is set, it is printed in {@code PrintProcessor.action} instead of a method name
     * @return action description
     */
    String value() default "";
}