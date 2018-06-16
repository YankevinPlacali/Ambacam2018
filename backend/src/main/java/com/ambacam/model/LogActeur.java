package com.ambacam.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LogActeur {

	private Object acteur;

	private Map<String, String> properties = new HashMap<>();

	public Object getActeur() {
		return acteur;
	}

	public void setActeur(Object acteur) {
		this.acteur = acteur;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public String getPropertiesAsString() {
		String propertiesAsString = String.join(", ",
				properties.keySet().stream().map(property -> String.format("%s:%s", property, properties.get(property)))
						.collect(Collectors.toSet()));
		return propertiesAsString;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
}
