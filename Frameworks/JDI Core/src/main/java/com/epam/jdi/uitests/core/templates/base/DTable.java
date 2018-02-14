package com.epam.jdi.uitests.core.templates.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;

public class DTable extends TTable implements ITable {
    public DTable() { setUseCache(false); }
}
