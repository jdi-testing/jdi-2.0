package com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface JSearch {
    /**
     * Returns root
     * @return root
     */
    FindBy root() default  @FindBy();

    /**
     * Returns input
     * @return input
     */
    FindBy input() default  @FindBy();

    /**
     * Returns search button
     * @return search button
     */
    FindBy searchButton() default  @FindBy();

    /**
     * Returns suggestions
     * @return suggestions
     */
    FindBy suggestions() default  @FindBy();

}
