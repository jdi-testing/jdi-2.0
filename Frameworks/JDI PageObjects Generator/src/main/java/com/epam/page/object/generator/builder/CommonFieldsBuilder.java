package com.epam.page.object.generator.builder;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.page.object.generator.model.Pairs;
import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.FieldSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jdi.tools.StringUtils.splitCamelCase;
import static com.squareup.javapoet.AnnotationSpec.builder;
import static java.lang.String.format;

public class CommonFieldsBuilder implements IFieldsBuilder {
    public Class elementClass;
    public CommonFieldsBuilder(Class elementClass) {
        this.elementClass = elementClass;
    }
    public List<FieldSpec> buildField(SearchRule searchRule, String url) throws IOException {
        List<FieldSpec> abstractFields = new ArrayList<>();
        List<String> elements = searchRule.getElements(url);

        for (String element : elements)
            abstractFields.add(FieldSpec.builder(elementClass, splitCamelCase(element))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(builder(FindBy.class)
                    .addMember("xpath", "$S", createXPathSelector(searchRule, element))
                    .build())
                .build());

        return abstractFields;
    }

    private String createXPathSelector(SearchRule searchRule, String element) {
        Pairs classes = new Pairs(searchRule.classes,
            key -> "@class", value -> value);
        Pairs attrs = Pairs.merge(searchRule.attributes, classes);
        attrs.add(searchRule.requiredAttribute.equals("text")
            ? "text()"
            : "@" + searchRule.requiredAttribute, element);
        return format("//%s[%s]", searchRule.tag, attrs.printAsXpath());
    }

}