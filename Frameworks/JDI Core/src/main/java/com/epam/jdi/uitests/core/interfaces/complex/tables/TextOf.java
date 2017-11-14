package com.epam.jdi.uitests.core.interfaces.complex.tables;

import com.epam.jdi.uitests.core.interfaces.common.IText;

/**
 * Created by Roman_Iovlev on 2/21/2017.
 */
public class TextOf {
    public static String textOf(IText textElement) {
        return textElement.getText();
    }
}
