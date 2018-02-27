package com.epam.jdi.uitests.core.interfaces.composite;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.annotations.Mandatory;
import com.epam.jdi.uitests.core.interfaces.base.IComposite;
import com.epam.jdi.uitests.core.interfaces.base.IHasValue;
import com.epam.jdi.uitests.core.interfaces.base.ISetValue;
import com.epam.jdi.uitests.core.interfaces.complex.FormFilters;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.jdi.tools.LinqUtils.foreach;
import static com.epam.jdi.tools.PrintUtils.print;
import static com.epam.jdi.tools.ReflectionUtils.getFields;
import static com.epam.jdi.tools.ReflectionUtils.getValueField;
import static com.epam.jdi.tools.StringUtils.LINE_BREAK;
import static com.epam.jdi.tools.StringUtils.namesEqual;
import static com.epam.jdi.tools.logger.LogLevels.STEP;
import static com.epam.jdi.tools.map.MapArray.toMapArray;
import static com.epam.jdi.uitests.core.actions.base.UIUtils.getButton;
import static com.epam.jdi.uitests.core.annotations.AnnotationsUtil.getElementName;
import static com.epam.jdi.uitests.core.interfaces.complex.FormFilters.MANDATORY;
import static com.epam.jdi.uitests.core.interfaces.complex.FormFilters.OPTIONAL;
import static com.epam.jdi.uitests.core.settings.JDISettings.asserter;
import static com.epam.jdi.uitests.core.utils.common.PrintUtils.getMapFromObject;
import static java.lang.String.format;

public interface IForm<T> extends IComposite, IHasValue {
    /**up
     * @param map Specify entity as map
     *            Fills all elements on the form which implements SetValue interface and can be matched with fields in input entity
     */
    @JDIAction("Fill form: {0}")
    default void fill(MapArray<String, String> map) {
        foreach(allFields(), element -> {
            String fieldValue = map.first((name, value) ->
                    namesEqual(name, getElementName(element)));
            if (fieldValue == null)
                return;
            ISetValue setValueElement = (ISetValue) getValueField(element, this);
            fillAction(setValueElement, fieldValue);
        });
        setFilterAll();
    }
    void fillAction(ISetValue element, String value);
    FormFilters getFilter();
    void setFilterAll();
    default List<Field> allFields() {
        switch (getFilter()) {
            case MANDATORY:
                return LinqUtils.where(getFields(this, ISetValue.class),
                        field -> field.isAnnotationPresent(Mandatory.class));
            case OPTIONAL:
                return LinqUtils.where(getFields(this, ISetValue.class),
                        field -> !field.isAnnotationPresent(Mandatory.class));
            default:
                return getFields(this, ISetValue.class);
        }
    }
    void setFilter(FormFilters filter);

    default IForm<T> onlyMandatory() {
        setFilter(MANDATORY);
        return this;
    }
    default IForm<T> onlyOptional() {
        setFilter(OPTIONAL);
        return this;
    }
    /**
     * @param entity Specify entity
     *               Fills all elements on the form which implements SetValue interface and can be matched with fields in input entity
     */
    @JDIAction("Fill form: {0}")
    default void fill(T entity) {
        fill(getMapFromObject(entity));
    }

    /**
     * @param map Specify entity as map
     *            Fills all elements on the form which implements SetValue interface and can be matched with fields in input entity
     */
    @JDIAction("Fill form: {0}")
    default void fill(Map<String, String> map) {
        fill(toMapArray(map));
    }

    /**
     * @param map Specify entity as mapArray
     *            Fills all elements on the form which implements SetValue interface and can be matched with fields in input entity
     */
    @JDIAction("Verify form value: {0}")
    default List<String> verify(MapArray<String, String> map) {
        List<String> compareFalse = new ArrayList<>();
        for (Field field : allFields()) {
            String fieldValue = map.first((name, value) ->
                namesEqual(name, getElementName(field)));
            if (fieldValue == null) continue;
            IHasValue valueField = (IHasValue) getValueField(field, this);
            String actual = valueField.getValue().trim();
            if (!actual.equals(fieldValue))
                compareFalse.add(format("Field '%s' (Actual: '%s' <> Expected: '%s')", field.getName(), actual, fieldValue));
        }
        setFilterAll();
        return compareFalse;
    }
    /**
     * @param entity Specify entity
     * Verify that form filled correctly. If not returns list of keys where verification fails
     */
    @JDIAction("Verify form: {0}")
    default List<String> verify(T entity) {
        return verify(getMapFromObject(entity));
    }

    /**
     * @param map Specify entity as map
     *            Verify that form filled correctly. If not returns list of keys where verification fails
     */
    @JDIAction("Verify form: {0}")
    default List<String> verify(Map<String, String> map) {
        return verify(toMapArray(map));
    }

    /**
     * @param map Specify entity as mapArray
     *            Verify that form filled correctly. If not throws error
     */
    @JDIAction("Check form: {0}")
    default void check(MapArray<String, String> map) {
        List<String> result = verify(map);
        asserter.isTrue(result.size() == 0,
            "Check form failed:" + LINE_BREAK + print(result, LINE_BREAK));
    }
    /**
     * @param entity Specify entity
     *               Verify that form filled correctly. If not throws error
     */
    @JDIAction("Check form: {0}")
    default void check(T entity) {
        check(getMapFromObject(entity));
    }

    /**
     * @param map Specify entity as map
     *               Verify that form filled correctly. If not throws error
     */
    @JDIAction("Check form: {0}")
    default void check(Map<String, String> map) {
        check(toMapArray(map));
    }

    /**
     * @param text Specify text
     *             Fill first setable field with value and click on Button “submit” <br>
     * @apiNote To use this option Form pageObject should have at least one ISetValue element and only one IButton Element
     */
    default void submit(String text) {
        submit(text, "submit");
    }

    /**
     * @param text       Specify text
     * @param buttonName button name for form submiting
     *                   Fill first setable field with value and click on Button “buttonName” <br>
     * @apiNote To use this option Form pageObject should have at least one ISetValue element <br>
     * Allowed different buttons to send one form e.g. save/ publish / cancel / search update ...
     */
    @JDIAction("{1}: {0}")
    default void submit(String text, String buttonName) {
        Field field = getFields(this, ISetValue.class).get(0);
        ISetValue setValueElement = (ISetValue) getValueField(field, this);
        fillAction(setValueElement, text);
        getButton(this, buttonName).click();
    }

    /**
     * @param text Specify text
     *             Fill first setable field with value and click on Button “add” or ”addButton” <br>
     * @apiNote To use this option Form pageObject should have at least one ISetValue element <br>
     * Allowed different buttons to send one form e.g. save/ publish / cancel / search update ...
     */
    @JDIAction("Add: {0}")
    default void add(String text) {
        submit(text, "add");
    }

    /**
     * @param text Specify text
     *             Fill first setable field with value and click on Button “select” or ”selectButton” <br>
     * @apiNote To use this option Form pageObject should have at least one ISetValue element <br>
     * Allowed different buttons to send one form e.g. save/ publish / cancel / search update ...
     */
    @JDIAction("Select: {0}")
    default void select(String text) {
        submit(text, "select");
    }

    /**
     * @param text Specify text
     *             Fill first setable field with value and click on Button “search” or ”searchButton” <br>
     * @apiNote To use this option Form pageObject should have at least one ISetValue element <br>
     * Allowed different buttons to send one form e.g. save/ publish / cancel / search update ...
     */
    @JDIAction("Search: {0}")
    default void search(String text) {
        submit(text, "search");
    }

    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “submit” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Submit {0}")
    default void submit(T entity) {
        submit(entity, "submit");
    }

    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “login” or ”loginButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Login as {0}")
    default void login(T entity) {
        submit(entity, "login");
    }
    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “login” or ”loginButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Login as {0}")
    default void loginAs(T entity) {
        login(entity);
    }

    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “login” or ”loginButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Send {0}")
    default void send(T entity) {
        submit(entity, "send");
    }
    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “add” or ”addButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Add {0}")
    default void add(T entity) {
        submit(entity, "add");
    }

    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “publish” or ”publishButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Publish {0}")
    default void publish(T entity) {
        submit(entity, "publish");
    }

    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “save” or ”saveButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Save {0}")
    default void save(T entity) {
        submit(entity, "save");
    }

    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “update” or ”updateButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Update {0}")
    default void update(T entity) {
        submit(entity, "update");
    }

    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “cancel” or ”cancelButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Fill {0} and cancel")
    default void cancel(T entity) {
        submit(entity, "cancel");
    }

    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “close” or ”closeButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Fill {0} and close")
    default void close(T entity) {
        submit(entity, "close");
    }

    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “back” or ”backButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Fill {0} and go back")
    default void back(T entity) {
        submit(entity, "back");
    }

    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “select” or ”selectButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Seelct {0}")
    default void select(T entity) {
        submit(entity, "select");
    }

    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “next” or ”nextButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Fill {0} and go to next")
    default void next(T entity) {
        submit(entity, "next");
    }

    /**
     * @param entity Specify entity
     *               Fill all SetValue elements and click on Button “search” or ”searchButton” <br>
     * @apiNote To use this option Form pageObject should have only one IButton Element
     */
    @JDIAction("Search for {0}")
    default void search(T entity) {
        submit(entity, "search");
    }

    /**
     * @param buttonName Specify Button Name
     * @param entity     Specify entity
     *                   Fill all SetValue elements and click on Button specified button e.g. "Publish" or "Save" <br>
     * @apiNote To use this option Form pageObject should have button names in specific format <br>
     * e.g. if you call "submit(user, "Publish") then you should have Element 'publishButton'. <br>
     * * Letters case in button name  no matters
     */
    @JDIAction("Submit {0} and press {1}")
    default void submit(T entity, String buttonName) {
        submit(getMapFromObject(entity), buttonName);
    }

    /**
     * @param buttonName Specify Button Name
     * @param entity     Specify entity
     *                   Fill all SetValue elements and click on Button specified button e.g. "Publish" or "Save" <br>
     * @apiNote To use this option Form pageObject should have button names in specific format <br>
     * e.g. if you call "submit(user, "Publish") then you should have Element 'publishButton'. <br>
     * * Letters case in button name  no matters
     */
    @JDIAction("Submit {0} and press {1}")
    default void submit(T entity, Enum buttonName){
        submit(entity, buttonName.toString().toLowerCase());
    }

    @JDIAction("Submit {0} and press {1}")
    default void submit(MapArray<String, String> objStrings, String name) {
        fill(objStrings);
        getButton(this, name).click();
    }
    /**
     * @param objStrings Fill all SetValue elements and click on Button specified button e.g. "Publish" or "Save" <br>
     * @apiNote To use this option Form pageObject should have button names in specific format <br>
     * e.g. if you call "submit(user, "Publish") then you should have Element 'publishButton'. <br>
     * * Letters case in button name  no matters
     */
    @JDIAction("Submit {0}")
    default void submit(MapArray<String, String> objStrings) {
        submit(objStrings, "submit");
    }

}