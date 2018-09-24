package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.common.IText;

public class TextOf {
    /**
     * @param textElement IText element with text
     * @return text of the Element
     */
    public static String textOf(IText textElement) {
        return textElement.getText();
    }
}
