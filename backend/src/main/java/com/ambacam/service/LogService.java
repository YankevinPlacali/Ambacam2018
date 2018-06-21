package com.ambacam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambacam.configuration.AppSettings;
import com.ambacam.model.Log;
import com.ambacam.repository.LogRepository;
import com.ambacam.search.logs.LogCriteria;
import com.ambacam.search.logs.LogSpecs;
import com.ambacam.transfert.logs.LogSearchTO;

@Service
@Transactional(rollbackFor = Exception.class)
public class LogService {
	@Autowired
	private LogRepository logRepository;

	@Autowired
	private AppSettings appSettings;

	/**
	 * Create a log
	 * 
	 * @param log
	 * 
	 * @return
	 */
	public Log create(Log log) {

		log.setId(null);

		return logRepository.save(log);
	}

	/**
	 * List all logs
	 * 
	 * @param limit
	 * @param page
	 * @param criteria
	 * @return
	 */
	public LogSearchTO list(Integer limit, Integer page, LogCriteria criteria) {

		Integer searchLimit = limit;
		Integer searchPage = page;

		// build specs
		LogSpecs specs = new LogSpecs(criteria);

		if (searchLimit == null || searchLimit < appSettings.getLogSearchDefaultPageSize()) {
			searchLimit = appSettings.getLogSearchDefaultPageSize();
		}

		if (searchPage == null || searchPage < appSettings.getLogSearchDefaultPageNumber()) {
			searchPage = appSettings.getLogSearchDefaultPageNumber();
		}

		Page<Log> pageLog = logRepository.findAll(specs,
				new PageRequest(searchPage, searchLimit, new Sort(Sort.Direction.ASC, "date")));

		LogSearchTO logSearchTO = new LogSearchTO();
		logSearchTO.setLogs(pageLog.getContent());
		logSearchTO.setPage(pageLog.getNumber());
		logSearchTO.setTotalPages(pageLog.getTotalPages());
		return logSearchTO;

	}

	public AppSettings getAppSettings() {
		return appSettings;
	}

	public void setAppSettings(AppSettings appSettings) {
		this.appSettings = appSettings;
	}

}
