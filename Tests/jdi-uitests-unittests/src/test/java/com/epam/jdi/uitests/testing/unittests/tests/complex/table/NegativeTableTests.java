package com.epam.jdi.uitests.testing.unittests.tests.complex.table;

import com.epam.jdi.uitests.testing.unittests.dataproviders.IndexesDP;
import com.epam.jdi.uitests.testing.unittests.tests.complex.table.base.SupportTableTestsBase;
import org.testng.annotations.Test;

/**
 * Created by Natalia_Grebenshchik on 10/28/2015.
 */
public class NegativeTableTests extends SupportTableTestsBase {

    //NB indexes lower and higher than scope give different types of exceptions
    @Test(expectedExceptions = Throwable.class,
            expectedExceptionsMessageRegExp = ".*Failed to do 'Get web element' action.*|Table indexes starts from -?[0-9]",
            dataProvider = "cellIndexes", dataProviderClass = IndexesDP.class)
    public void illegalCellIndexTest(int columnIndex, int rowIndex) {
            table().cell(columnIndex, rowIndex).getValue();
    }

    //NB indexes lower and higher than scope give different types of exceptions
    @Test(expectedExceptions = AssertionError.class,
            expectedExceptionsMessageRegExp = "Can't Get Row '-?[0-9]+'. \\[num\\] > ColumnsCount\\([0-9]+\\)."
            +"|Table indexes starts from -?[0-9]",
            dataProvider = "indexes", dataProviderClass = IndexesDP.class)
    public void illegalRowIndexTest(int rowIndex){
        table().row(rowIndex);
    }

    // NB indexes lower and higher than scope give different types of exceptions
    @Test(expectedExceptions = AssertionError.class,
            expectedExceptionsMessageRegExp = "Can't Get Column '[-0-9]*'. \\[num\\] > RowsCount\\([0-9]+\\)."
            +"|Table indexes starts from -?[0-9]",
            dataProvider = "indexes", dataProviderClass = IndexesDP.class)
    public void illegalColumnIndexTest(int columnIndex){
        table().column(columnIndex);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void illegalHeaderName(){
    table().header().get("Column_illegal").select();
}

    @Test(expectedExceptions = NullPointerException.class)
    public void illegalHeaderIndex(){
         table().rows().header().get("Row_illegal").select();
    }

}
