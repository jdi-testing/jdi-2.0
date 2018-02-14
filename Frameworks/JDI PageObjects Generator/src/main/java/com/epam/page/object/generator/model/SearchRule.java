package com.epam.page.object.generator.model;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import static com.epam.jdi.tools.LinqUtils.where;
import static java.util.Arrays.asList;

public class SearchRule {

    public String type;
    public String tag;
    public String requiredAttribute;
    public List<String> classes;
    public Pairs attributes;

    public SearchRule(JSONObject jsonObject) {
        type = ((String) jsonObject.get("type")).toLowerCase();
        requiredAttribute = (String) jsonObject.get("name");
        String rulesString = (String) jsonObject.get("rules");
        Pairs rules = new Pairs(asList(rulesString.split(";")),
                r -> r.split("=")[0],
                r -> r.split("=")[1]);
        tag = rules.first(key -> key.equals("tag"));
        classes = rules.filter(
                key -> key.equals("class"));
        attributes = rules;
    }
    public List<String> getElements(String url) throws IOException {
        return requiredAttribute.equals("text")
            ? extractElementsFromWebSite(url).eachText()
            : extractElementsFromWebSite(url).eachAttr(requiredAttribute);
    }

	public Elements extractElementsFromWebSite(String url) throws IOException {
        Elements searchResults = new Elements();
        Document document = getURLConnection(url);
        searchResults.addAll(searchElementsByTag(document));
        searchResults.retainAll(searchElementsByClasses(document));
        searchResults.retainAll(searchElementsByAttributes(document));
        return new Elements(searchResults);
    }

    private Elements searchElementsByTag(Document document) {
        return tag != null
            ? document.select(tag)
            : document.getAllElements();
    }

    private Elements searchElementsByClasses(Document document) {
        return !classesAreEmpty()
                ? document.select(prepareCSSQuerySelector())
                : document.getAllElements();
    }

    private Elements searchElementsByAttributes(Document document) {
        return !attributesAreEmpty()
            ? new Elements(where(document.getAllElements(),
                this::elementAttributesMatch))
            : document.getAllElements();
    }

    private boolean elementAttributesMatch(Element element) {
        return attributes.stream().noneMatch(elementAttribute -> element.attr(elementAttribute.getName()) == null
                || !element.attr(elementAttribute.getName()).equals(elementAttribute.getValue()));
    }

    private String prepareCSSQuerySelector() {
        StringBuilder selector = new StringBuilder();
        classes.forEach(clazz -> selector.append(".").append(clazz));
        return selector.toString();
    }

    private Document getURLConnection(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public boolean classesAreEmpty() {
        return classes == null || classes.isEmpty();
    }

    public boolean attributesAreEmpty() {
        return attributes == null || attributes.isEmpty();
    }

}