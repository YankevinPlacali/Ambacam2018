package com.ambacam.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ambacam2018.app.settings")
public class AppSettings {

	private String projectName;

	private int searchDefaultPageSize;

	private int searchDefaultPageNumber;

	private String crossOrigin;

	private String authorizationUsername;

	private String authorizationResourceId;

	private String authorizationSecret;

	private int accessTokenValidity;

	private String defaultOperateurUsername;

	private String defaultOperateurPassword;

	private String defaultOperateurFirstname;

	private String defaultOperateurLastname;

	private String defaultOperateurGender;

	private String defaultCountryName;

	private String defaultCountryDescription;

	private String defaultMotifSuppressionName;

	private String defaultMotifSuppressionDescription;

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

	public String getAuthorizationUsername() {
		return authorizationUsername;
	}

	public void setAuthorizationUsername(String authorizationUsername) {
		this.authorizationUsername = authorizationUsername;
	}

	public String getAuthorizationResourceId() {
		return authorizationResourceId;
	}

	public void setAuthorizationResourceId(String authorizationResourceId) {
		this.authorizationResourceId = authorizationResourceId;
	}

	public String getAuthorizationSecret() {
		return authorizationSecret;
	}

	public void setAuthorizationSecret(String authorizationSecret) {
		this.authorizationSecret = authorizationSecret;
	}

	public int getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(int accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
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

	public String getDefaultMotifSuppressionName() {
		return defaultMotifSuppressionName;
	}

	public void setDefaultMotifSuppressionName(String defaultMotifSuppressionName) {
		this.defaultMotifSuppressionName = defaultMotifSuppressionName;
	}

	public String getDefaultMotifSuppressionDescription() {
		return defaultMotifSuppressionDescription;
	}

	public void setDefaultMotifSuppressionDescription(String defaultMotifSuppressionDescription) {
		this.defaultMotifSuppressionDescription = defaultMotifSuppressionDescription;
	}

}