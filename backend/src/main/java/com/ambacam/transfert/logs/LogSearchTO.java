package com.ambacam.transfert.logs;

import java.util.List;

import com.ambacam.model.Log;

public class LogSearchTO {

	private int page;

	private int totalPages;

	private List<Log> logs;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPage) {
		this.totalPages = totalPage;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

}
