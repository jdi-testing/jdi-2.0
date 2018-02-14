package com.epam.page.object.generator;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static java.util.Arrays.asList;

public class Generate {

    // EXAMPLE
    public static void main(String[] args) throws ParseException, IOException, URISyntaxException {
        List<String> urls = asList(
            "https://www.w3schools.com/html/html_forms.asp",
            "https://www.w3schools.com/css/default.asp",
            "https://www.w3schools.com/html/html_form_input_types.asp");

        new PageObjectsGenerator(
                "src/test/resources/webElement.json",
                urls,
                "D:/Work/Projects/Java/temp/jdi-generator-example/src/main/java/",
                "w3c.tests")
                .generatePageObjects();
    }
}
