package com.epam.jdi.uitests.core.annotations;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.logger.LogLevels;

import java.lang.annotation.*;

import static com.epam.jdi.uitests.core.logger.LogLevels.STEP;

/**
 * Indicates action on web element
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface JDIAction {

    /**
     * @return description of the action
     */
    String value() default "";
    
    /**
     * Returns the level of logging applicable to this method
     * @return level of logging
     */
    LogLevels level() default STEP;
}