package com.epam.page.object.generator.builder;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.FieldSpec;

import java.io.IOException;
import java.util.List;

public interface IFieldsBuilder {
	List<FieldSpec> buildField(SearchRule searchRule, String url) throws IOException;

}