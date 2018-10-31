package com.gemini.ambacam.transfert.logs;

import java.util.Date;

public class LogSearch {

	private String keyword;

	private Date dateBefore;

	private Date dateAfter;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getDateBefore() {
		return dateBefore;
	}

	public void setDateBefore(Date dateBefore) {
		this.dateBefore = dateBefore;
	}

	public Date getDateAfter() {
		return dateAfter;
	}

	public void setDateAfter(Date dateAfter) {
		this.dateAfter = dateAfter;
	}

}
