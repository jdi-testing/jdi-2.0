package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.lang.reflect.Field;

/**
 * Interface for element with name
 */
public interface INamed {
    /**
     * @return elements name
     */
    String getName();

    /**
     * @param field Specify element name
     */
    void setName(Field field);
}
