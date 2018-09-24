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
public @interface JPagination {
    /**
     * Returns root
     * @return root
     */
    FindBy root()  default @FindBy();

    /**
     * Returns next
     * @return next
     */
    FindBy next()  default @FindBy();

    /**
     * Returns prev
     * @return prev
     */
    FindBy prev()  default @FindBy();

    /**
     * Returns first
     * @return first
     */
    FindBy first() default @FindBy();

    /**
     * Returns last
     * @return last
     */
    FindBy last()  default @FindBy();

    /**
     * Returns page
     * @return page
     */
    FindBy page()  default @FindBy();
}
