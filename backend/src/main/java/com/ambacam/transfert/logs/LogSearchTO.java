package com.ambacam.transfert.logs;

import java.util.List;

import com.ambacam.model.Log;
import com.ambacam.transfert.SearchResultTO;

public class LogSearchTO extends SearchResultTO {

	private List<Log> logs;

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

}
