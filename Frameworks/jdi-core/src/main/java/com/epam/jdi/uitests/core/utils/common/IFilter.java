package com.epam.jdi.uitests.core.utils.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc1;

/**
 * Generic interface, extends functional interface JFunc1
 * @param <Type>
 */
public interface IFilter<Type> extends JFunc1<Type, Boolean> {
}
