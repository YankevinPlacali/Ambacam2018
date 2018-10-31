package com.gemini.ambacam.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ambacam2018.app.settings")
public class AppSettings {

	private String projectName;

	private int searchDefaultPageSize;

	private int searchDefaultPageNumber;

	private String crossOrigin;

	private String defaultOperateurUsername;

	private String defaultOperateurPassword;

	private String defaultOperateurFirstname;

	private String defaultOperateurLastname;

	private String defaultOperateurGender;

	private String defaultCountryName;

	private String defaultCountryDescription;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getSearchDefaultPageSize() {
		return searchDefaultPageSize;
	}

	public void setSearchDefaultPageSize(int searchDefaultPageSize) {
		this.searchDefaultPageSize = searchDefaultPageSize;
	}

	public int getSearchDefaultPageNumber() {
		return searchDefaultPageNumber;
	}

	public void setSearchDefaultPageNumber(int searchDefaultPageNumber) {
		this.searchDefaultPageNumber = searchDefaultPageNumber;
	}

	public String getCrossOrigin() {
		return crossOrigin;
	}

	public void setCrossOrigin(String crossOrigin) {
		this.crossOrigin = crossOrigin;
	}

	public String getDefaultOperateurUsername() {
		return defaultOperateurUsername;
	}

	public void setDefaultOperateurUsername(String defaultOperateurUsername) {
		this.defaultOperateurUsername = defaultOperateurUsername;
	}

	public String getDefaultOperateurPassword() {
		return defaultOperateurPassword;
	}

	public void setDefaultOperateurPassword(String defaultOperateurPassword) {
		this.defaultOperateurPassword = defaultOperateurPassword;
	}

	public String getDefaultOperateurFirstname() {
		return defaultOperateurFirstname;
	}

	public void setDefaultOperateurFirstname(String defaultOperateurFirstname) {
		this.defaultOperateurFirstname = defaultOperateurFirstname;
	}

	public String getDefaultOperateurLastname() {
		return defaultOperateurLastname;
	}

	public void setDefaultOperateurLastname(String defaultOperateurLastname) {
		this.defaultOperateurLastname = defaultOperateurLastname;
	}

	public String getDefaultOperateurGender() {
		return defaultOperateurGender;
	}

	public void setDefaultOperateurGender(String defaultOperateurGender) {
		this.defaultOperateurGender = defaultOperateurGender;
	}

	public String getDefaultCountryName() {
		return defaultCountryName;
	}

	public void setDefaultCountryName(String defaultCountryName) {
		this.defaultCountryName = defaultCountryName;
	}

	public String getDefaultCountryDescription() {
		return defaultCountryDescription;
	}

	public void setDefaultCountryDescription(String defaultCountryDescription) {
		this.defaultCountryDescription = defaultCountryDescription;
	}

}