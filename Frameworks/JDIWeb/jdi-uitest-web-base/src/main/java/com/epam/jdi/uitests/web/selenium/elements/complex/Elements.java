package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.core.annotations.Title;
import com.epam.jdi.uitests.core.interfaces.common.ILabel;
import com.epam.jdi.uitests.core.interfaces.common.IText;
import com.epam.jdi.uitests.core.interfaces.complex.IList;
import com.epam.jdi.uitests.web.selenium.elements.WebCascadeInit;
import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.common.Text;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jdi.tools.EnumUtils.getEnumValue;
import static com.epam.jdi.tools.ReflectionUtils.getValueField;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

public class Elements<T extends BaseElement> extends BaseElement implements IList<T> {
    private MapArray<String, T> elements;
    private List<T> values;
    private Class<T> classType;
    public String titleFieldName = NO_TITLE_FIELD;
    public static final String NO_TITLE_FIELD = "NO TITLE FIELD";

    public Elements(Class<T> classType) {
        this.classType = classType != null ? classType : (Class<T>) Button.class;
        elements = new MapArray<>();
        values = new ArrayList<>();
        setUseCache(true);
    }
    public void refresh() {
        elements.clear();
        values.clear();
    }
    private List<WebElement> getWebElements() {
        return getElements();
    }
    public List<T> values() {
        if (isUseCache()) {
            if (!values.isEmpty())
                return values;
            if (!elements.isEmpty())
                return elements.values();
        } else values.clear();
        return values = LinqUtils.select(getWebElements(), this::initElement);
    }
    public MapArray<String, T> getAll() {
        if (isUseCache())
            if (!elements.isEmpty())
                return elements;
        else  { elements.clear(); values.clear(); }
        return elements = values.isEmpty()
            ? new MapArray<>(getWebElements(),
                this::elementTitle,
                this::initElement)
            : new MapArray<>(
                LinqUtils.select(getWebElements(), this::elementTitle),
                values);
    }
    @Override
    public boolean isEmpty() {
        return getWebElements().size() == 0;
    }

    private String elementTitle(WebElement el) {
        if (titleFieldName == null)
            identifyTitleField();
        return titleFieldName.equals(NO_TITLE_FIELD)
                ? el.getText()
                : getElementTitle(el, titleFieldName);
    }
    private String getElementTitle(WebElement el, String titleField) {
        T element = initElement(el);
        Field field = null;
        try { field = element.getClass().getField(titleField);
        } catch (NoSuchFieldException ex) { /* if titleField defined then field always exist */ }
        return ((IText) getValueField(field, element)).getText();
    }

    private T initElement(WebElement el) {
        try {
            T element = classType.newInstance();
            element.setEngine(el);
            element.setParent(this);
            new WebCascadeInit().initElements(element, engine().getDriverName());
            return element;
        } catch (Exception ex) {
            throw exception("Can't instantiate list element");
        }
    }

    public <E> List<E> asData(Class<E> entityClass) {
        return map((k, v) -> v.asEntity(entityClass));
    }
    private boolean isTextElement(Field field) {
        return field.getType().equals(Text.class) || field.getType().equals(IText.class);
    }
    private boolean isLabelElement(Field field) {
        return field.getType().equals(Label.class) || field.getType().equals(ILabel.class);
    }
    public T get(String name) {
        return getAll().get(name);
    }
    private void identifyTitleField() {
        Field[] fields = classType.getFields();

        // Get title from annotation
        List<Field> expectedFields = LinqUtils.where(fields, f -> f.isAnnotationPresent(Title.class));
        switch (expectedFields.size()) {
            case 0: break;
            case 1: titleFieldName = expectedFields.get(0).getName(); return;
            default: exception("Entity should have only 1 @Title annotation. Please correct '%s' class", classType.getSimpleName());
        }
        // Get title from Label field
        expectedFields = LinqUtils.where(fields, this::isLabelElement);
        titleFieldName = expectedFields.size() == 1
            ? expectedFields.get(0).getName()
            : NO_TITLE_FIELD;
    }

    public T get(Enum name) {
        return get(getEnumValue(name));
    }


}