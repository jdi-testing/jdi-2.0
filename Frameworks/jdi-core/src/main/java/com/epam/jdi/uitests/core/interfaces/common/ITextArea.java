package com.epam.jdi.uitests.core.interfaces.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;

import static com.epam.jdi.uitests.core.actions.common.TextFieldActions.addNewLine;

/**
 * Interface for TextAreas
 */
public interface ITextArea extends ITextField {
    /**
     * Clear textarea and Input several lines of text in textarea
     *
     * @param textRows Lines of text to be set
     */
    @JDIAction
    default void inputLines(String... textRows) {
        clear();
        for (String row : textRows)
            addNewLine.execute(this, row);
    }

    /**
     * Add text in textarea from new line (without clearing previous)
     *
     * @param textRow Line to be add to the textarea
     */
    @JDIAction
    default void addNewLine(String textRow) {
        addNewLine.execute(this, textRow);
    }

    /**
     * Get lines of text in textarea
     *
     * @return Lines of text in textarea
     */
    @JDIAction
    default String[] getLines() {
        return getText().split("\\n");
    }
}