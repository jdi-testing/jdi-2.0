package com.epam.jdi.uitests.testing.unittests.tests.complex.table;

import com.epam.jdi.uitests.testing.unittests.tests.complex.table.base.SupportTableTestsBase;
import org.testng.annotations.Test;

import static com.epam.jdi.uitests.core.interfaces.complex.tables.Column.inColumn;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.Row.inRow;
import static com.epam.jdi.uitests.core.utils.common.Filters.equalsTo;

/**
 * Created by Natalia_Grebenshchikova on 10/5/2015.
 */
public class WaiterTests extends SupportTableTestsBase {

    @Test
    public void waitExpectedRowsValueTest() {
        table().assertThat().hasCell(equalsTo("Cucumber, Jbehave, Thucydides, SpecFlow"), inRow(6));
    }

    @Test
    public void waitUnexpectedRowsValueTest() {
        table().assertThat().hasNoCell(equalsTo("Cucumber, Jbehave, Thucydides, SpecFlow Unexepected"), inRow(6));
    }

    @Test
    public void waitExpectedColumnsValueTest() {
        table().assertThat().hasCell(equalsTo("Custom"), inColumn(2));
    }

    @Test
    public void waitUnexpectedColumnsValueTest() {
        table().assertThat().hasCell(equalsTo("Custom Unexpected"), inColumn(2));
    }

    @Test
    public void tableIsEmptyTest(){
        table().assertThat().isNotEmpty();
    }

}
