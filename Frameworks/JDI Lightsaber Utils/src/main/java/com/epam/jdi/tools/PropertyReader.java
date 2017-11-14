package com.epam.jdi.tools;
/* The MIT License
 *
 * Copyright 2004-2017 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */

/**
 * Created by Roman Iovlev on 10.27.2017
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Consumer;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public final class PropertyReader {
    private static String propertiesPath;
    private static volatile Properties properties;
    private static InputStream inputStream;

    private PropertyReader() {
    }
    private static String getCorrectPath() {
        if (propertiesPath.charAt(0) != '/')
            propertiesPath = "/" + propertiesPath;
        return propertiesPath;
    }

    public static Properties readProperties() throws IOException {
        properties = new Properties();
        try {
            inputStream = PropertyReader.class.getResourceAsStream(getCorrectPath());
            if (inputStream != null)
                properties.load(inputStream);
        } catch (Exception ex) {
            if (inputStream != null) inputStream.close();
        }
        return properties;
    }

    private static Properties loadProperties() throws IOException {
        return properties != null ? properties : readProperties();
    }

    public static Properties getProperties(String path) throws IOException {
        propertiesPath = path;
        return readProperties();
    }


    public static String getProperty(String propertyName) throws IOException {
        return loadProperties().getProperty(propertyName);
    }

    public static void fillAction(Consumer<String> action, String name) {
        String prop = null;
        try {
            prop = getProperty(name);
        } catch (Exception ignore) {}
        if (isNotBlank(prop))
            action.accept(prop);
    }

}