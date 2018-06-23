package com.ambacam.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ambacam2018.app.settings")
public class AppSettings {

	private String projectName;

	private int searchDefaultPageSize;

	private int searchDefaultPageNumber;

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

}