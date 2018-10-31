package com.gemini.ambacam.transfert.requetegroupes;

public class RequeteGroupeUpdateTO {

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RequeteGroupeUpdateTO description(String description) {
		this.description = description;
		return this;
	}

}
