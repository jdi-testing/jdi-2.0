package com.epam.page.object.generator.parser;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.page.object.generator.model.SearchRule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

public class JSONIntoRuleParser {

	private String jsonPath;
	private final JSONParser parser = new JSONParser();
	private Set<String> supportedTypes;

	public JSONIntoRuleParser(String jsonPath, Set<String> supportedTypes) {
		this.jsonPath = jsonPath;
		this.supportedTypes = supportedTypes;
	}

	/**
	 * Parsing searching rules from JSON file.
	 * @return List of search rules from JSON file.
	 * @throws IOException If can't open JSON file.
	 * @throws ParseException If JSON has invalid format.
	 */
	public List<SearchRule> getRulesFromJSON() throws IOException, ParseException {
		try (BufferedReader br = new BufferedReader(new FileReader(jsonPath))) {
			JSONObject fullJSON = (JSONObject) parser.parse(br);
			JSONArray elements = (JSONArray) fullJSON.get("elements");
			List<SearchRule> searchRules = new ArrayList<>();
			for (Object element : elements) {
				JSONObject jsonObject = (JSONObject) element;
				String type = ((String) jsonObject.get("type")).toLowerCase();
				if (supportedTypes.contains(type))
					searchRules.add(new SearchRule(jsonObject));
				else throw new ParseException(1,
					format("Unsupported element type '%s'. Supported types: %s",
					type, supportedTypes));
			}
			return searchRules;
		}
	}
}