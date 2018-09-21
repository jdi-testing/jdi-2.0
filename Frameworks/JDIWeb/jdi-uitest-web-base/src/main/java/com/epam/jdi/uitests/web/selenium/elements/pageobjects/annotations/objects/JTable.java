package com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.complex.tables.TableHeaderTypes;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.epam.jdi.uitests.core.interfaces.complex.tables.TableHeaderTypes.COLUMNS_HEADERS;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface JTable {
    /**
     * Returns root
     * @return root
     */
    FindBy root() default @FindBy();
    /**
     * Returns header
     * @return header
     */
    String[] header() default {};
    /**
     * Returns rows header
     * @return rows header
     */
    String[] rowsHeader() default {};

    /**
     * Returns headers
     * @return headers
     */
    FindBy headers() default @FindBy();

    /**
     * Returns row names
     * @return row names
     */
    FindBy rowNames() default @FindBy();

    /**
     * Returns cell
     * @return cell
     */
    FindBy cell() default @FindBy();

    /**
     * Returns cells
     * @return cells
     */
    FindBy cells() default @FindBy();

    /**
     * Returns row
     * @return row
     */
    FindBy row() default @FindBy();

    /**
     * Returns column
     * @return column
     */
    FindBy column() default @FindBy();

    /**
     * Returns footer
     * @return footer
     */
    FindBy footer() default @FindBy();

    /**
     * Returns height
     * @return height
     */
    int height() default -1;

    /**
     * Returns width
     * @return width
     */
    int width() default -1;

    /**
     * Returns size
     * @return size
     */
    String size() default "";

    /**
     * Returns row start index
     * @return row start index
     */
    int rowStartIndex() default -1;

    /**
     * Returns column start index
     * @return column start index
     */
    int colStartIndex() default -1;

    /**
     * Returns header type
     * @return header type
     */
    TableHeaderTypes headerType() default COLUMNS_HEADERS;

    /**
     * Returns use cache
     * @return use cache
     */
    boolean useCache() default false;


}