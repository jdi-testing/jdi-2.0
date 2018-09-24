package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.DataClass;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.firstIndex;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class NameNum extends DataClass<NameNum> {
    public int num = 0;
    public String name;

    /**
     * @return 'true' if the name is not blank, false otherwise
     */
    public boolean hasName() {
        return isNotBlank(name);
    }

    /**
     * @return Returns a String with <br>
     *     name and index, if name is is not blank and index is positive; <br>
     *     only name, if name is is not blank and index is not positive; <br>
     *     index, if name is blank
     */
    @Override
    public String toString() {
        return hasName()
                ? ((num > 0) ? super.toString() : name)
                : (num + "");
    }

    /**
     * @param headers the list of values headers
     * @return value index according to the list of values headers
     */
    public int getIndex(List<String> headers) {
        return !hasName() ? num : firstIndex(headers,
                h -> equalsIgnoreCase(h, name));
    }
}