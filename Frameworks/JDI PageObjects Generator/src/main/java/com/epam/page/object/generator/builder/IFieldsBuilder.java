package com.epam.page.object.generator.builder;

import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.FieldSpec;

import java.io.IOException;
import java.util.List;

public interface IFieldsBuilder {
	List<FieldSpec> buildField(SearchRule searchRule, String url) throws IOException;

}