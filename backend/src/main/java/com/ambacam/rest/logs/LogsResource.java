package com.ambacam.rest.logs;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambacam.model.Log;
import com.ambacam.rest.ApiConstants;
import com.ambacam.search.logs.LogCriteria;
import com.ambacam.service.LogService;
import com.ambacam.transfert.SearchResultTO;

@RestController
@RequestMapping(ApiConstants.LOG_COLLECTION)
@Validated
public class LogsResource {

	private static final Logger log = LoggerFactory.getLogger(LogsResource.class);

	@Autowired
	private LogService logService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SearchResultTO<Log> list(@QueryParam("limit") Integer limit, @QueryParam("page") Integer page,
			@RequestBody @Valid LogCriteria criteria) {
		log.info("Find logs by parameters");

		return logService.list(limit, page, criteria);

	}
}
