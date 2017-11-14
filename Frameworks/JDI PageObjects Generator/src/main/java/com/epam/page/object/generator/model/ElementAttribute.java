package com.epam.page.object.generator.model;

public class ElementAttribute {
	private String name;
	private String value;

	public ElementAttribute(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}

}