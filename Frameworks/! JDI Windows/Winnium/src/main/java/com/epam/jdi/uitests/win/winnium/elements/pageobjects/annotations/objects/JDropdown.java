package com.epam.jdi.uitests.win.winnium.elements.pageobjects.annotations.objects;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface JDropdown {
    FindBy expand() default @FindBy();
    FindBy elementByName() default @FindBy();
}
