package com.epam.jdi.uitests.core.templates.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.base.INamed;

import java.lang.reflect.Field;

import static com.epam.jdi.uitests.core.annotations.AnnotationsUtil.getElementName;

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
