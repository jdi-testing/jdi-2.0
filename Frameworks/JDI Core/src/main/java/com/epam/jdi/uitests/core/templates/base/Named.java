package com.epam.jdi.uitests.core.templates.base;

import com.epam.jdi.uitests.core.interfaces.base.INamed;

import java.lang.reflect.Field;

import static com.epam.jdi.uitests.core.annotations.AnnotationsUtil.getElementName;

/**
 * Created by Roman_Iovlev on 10/30/2017.
 */
public class Named implements INamed {
    private String name;
    private String fieldName;
    private String typeName;

    public String getName() {
        return name != null ? name : getTypeName();
    }
    public void setName(Field field) {
        name = getElementName(field);
        fieldName = field.getName();
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFieldName() {
        return fieldName != null ? fieldName : getName();
    }
    protected String getTypeName() {
        return (typeName != null) ? typeName : getClass().getSimpleName();
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
