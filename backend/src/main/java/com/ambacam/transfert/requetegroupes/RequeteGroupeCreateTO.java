package com.ambacam.transfert.requetegroupes;

public class RequeteGroupeCreateTO {

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RequeteGroupeCreateTO description(String description) {
		this.description = description;
		return this;
	}

}
