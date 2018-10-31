package com.gemini.ambacam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gemini.ambacam.configuration.AppSettings;
import com.gemini.ambacam.model.Log;
import com.gemini.ambacam.repository.LogRepository;
import com.gemini.ambacam.search.logs.LogCriteria;
import com.gemini.ambacam.search.logs.LogSpecs;
import com.gemini.ambacam.transfert.SearchResultTO;

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
	public SearchResultTO<Log> list(Integer limit, Integer page, LogCriteria criteria) {

		Integer searchLimit = limit;
		Integer searchPage = page;

		// build specs
		LogSpecs specs = new LogSpecs(criteria);

		if (searchLimit == null || searchLimit < appSettings.getSearchDefaultPageSize()) {
			searchLimit = appSettings.getSearchDefaultPageSize();
		}

		if (searchPage == null || searchPage < appSettings.getSearchDefaultPageNumber()) {
			searchPage = appSettings.getSearchDefaultPageNumber();
		}

		Page<Log> pageLog = logRepository.findAll(specs,
				new PageRequest(searchPage, searchLimit, new Sort(Sort.Direction.ASC, "date")));

		SearchResultTO<Log> logSearchTO = new SearchResultTO<Log>();
		logSearchTO.setContent(pageLog.getContent());
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
