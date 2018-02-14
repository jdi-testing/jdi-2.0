package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;

import java.util.List;

public interface ISearch extends IComboBox {
    /**
     * @param text Specify Text to search
     *             Input text in search field and press search button
     */
    @JDIAction
    default void find(String text) {
        input(text);
        click();
    }
    @JDIAction
    default void search(String text) { find(text);}
    @JDIAction
    default List<String> getSuggestions(String subString) {
        input(subString);
        return ((ISelector)linked().get("suggestions")).getLabels();
    }
    @JDIAction
    default void selectSuggestion(String subString, String name) {
        input(subString);
        ((ISelector)linked().get("suggestions")).select(name);
        click();
    }
}