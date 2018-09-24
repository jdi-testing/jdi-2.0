package com.epam.jdi.uitests.core.interfaces;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.lang.reflect.Field;


public interface ISetup {

    /**
     * Sets up a complex or custom UI element.
     * {@code setup} is used during the cascade initialisation of elements from JDI annotations
     * @param field an element to setup (class variable or interface variable)
     */
    void setup(Field field);
}
