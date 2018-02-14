package com.epam.jdi.cucumber;

public enum FormActions {

    SUBMIT("submit"), FILL("fill"), CHECK("check");

    private final String text;

    FormActions(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
