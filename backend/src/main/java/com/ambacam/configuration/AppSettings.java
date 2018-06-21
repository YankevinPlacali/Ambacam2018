package com.ambacam.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ambacam2018.app.settings")
public class AppSettings {

	private String projectName;

	private int logSearchDefaultPageSize;

	private int logSearchDefaultPageNumber;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getLogSearchDefaultPageSize() {
		return logSearchDefaultPageSize;
	}

	public void setLogSearchDefaultPageSize(int logSearchDefaultPageSize) {
		this.logSearchDefaultPageSize = logSearchDefaultPageSize;
	}

	public int getLogSearchDefaultPageNumber() {
		return logSearchDefaultPageNumber;
	}

	public void setLogSearchDefaultPageNumber(int logSearchDefaultPageNumber) {
		this.logSearchDefaultPageNumber = logSearchDefaultPageNumber;
	}

}