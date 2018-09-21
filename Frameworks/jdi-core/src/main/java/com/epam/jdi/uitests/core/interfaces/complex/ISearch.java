package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;

import java.util.List;

public interface ISearch extends IComboBox {

    /**
     * Inputs text in the search field and presses search button
     * @param text the text to search
     */
    @JDIAction
    default void find(String text) {
        input(text);
        click();
    }

    @JDIAction
    default void search(String text) {
        find(text);
    }

    /**
     * Inputs text in the search field and gets suggestions
     * @param subString the text to input
     * @return a list of suggestions labels
     */
    @JDIAction
    default List<String> getSuggestions(String subString) {
        input(subString);
        return ((ISelector) linked().get("suggestions")).getLabels();
    }

    /**
     * Inputs text in the search field, gets suggestions and clicks on one of them by its name
     * @param subString the text to input
     * @param name      the name of suggestion to click on
     */
    @JDIAction
    default void selectSuggestion(String subString, String name) {
        input(subString);
        ((ISelector) linked().get("suggestions")).select(name);
        click();
    }
}