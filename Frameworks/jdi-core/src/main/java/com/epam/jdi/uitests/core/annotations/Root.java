package com.epam.jdi.uitests.core.annotations;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.lang.annotation.*;

/**
 * {@code @Root} annotation is used for breaking the cascade locators chain.
 * For example if some section has "locator1" and element on it has "locator2",
 * then search locator would be "locator1 + locator2".
 * But if you put {@code @Root} on element, then locator will be only "locator2"
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
public @interface Root {
}