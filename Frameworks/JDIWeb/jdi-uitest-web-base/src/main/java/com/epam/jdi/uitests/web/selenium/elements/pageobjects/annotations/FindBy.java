package com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.lang.annotation.*;

import static com.epam.jdi.uitests.core.settings.JDIData.APP_VERSION;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Repeatable(FindBys.class)
public @interface FindBy {
    // Selenium
    /**
     * Returns css
     * @return
     */
    String css() default "";

    /**
     * Returns tag name
     * @return tag name
     */
    String tagName() default "";

    /**
     * Returns link text
     * @return link text
     */
    String linkText() default "";

    /**
     * Returns partial link text
     * @return partial link text
     */
    String partialLinkText() default "";

    /**
     * Returns xpath
     * @return xpath
     */
    String xpath() default "";

    // Text
    /**
     * Returns text
     * @return text
     */
    String text() default "";

    //Attributes
    /**
     * Returns id
     * @return id
     */
    String id() default "";

    /**
     * Returns name
     * @return name
     */
    String name() default "";

    /**
     * Returns class name
     * @return class name
     */
    String className() default "";

    /**
     * Returns value
     * @return value
     */
    String value() default "";

    /**
     * Returns title
     * @return title
     */
    String title() default "";

    // Angular
    /**
     * Returns model
     * @return model
     */
    String model() default "";

    /**
     * Returns binding
     * @return binding
     */
    String binding() default "";

    /**
     * Returns repeat
     * @return repeat
     */
    String repeat() default "";

    // Group
    /**
     * Returns group
     * @return group
     */
    String group() default APP_VERSION;
}