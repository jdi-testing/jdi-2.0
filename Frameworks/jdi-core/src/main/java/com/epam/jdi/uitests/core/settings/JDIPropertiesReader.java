package com.epam.jdi.uitests.core.settings;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.PropertyReader;

import java.util.Properties;

public class JDIPropertiesReader {

    /**
     * Returns properties from a file
     * @param path a path to properties file
     * @return Properties
     */
    public static Properties getProperties(String path) {
        Properties p = PropertyReader.getProperties("/../../target/classes/" + path);
        return p.size() > 0 ? p : PropertyReader.getProperties(path);
    }

}
