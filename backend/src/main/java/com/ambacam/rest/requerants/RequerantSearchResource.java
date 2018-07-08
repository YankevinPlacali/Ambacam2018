package com.ambacam.rest.requerants;

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

import com.ambacam.rest.ApiConstants;
import com.ambacam.search.requerants.RequerantCriteria;
import com.ambacam.service.RequerantService;
import com.ambacam.transfert.SearchResultTO;
import com.ambacam.transfert.requerants.RequerantReadTO;

@RestController
@RequestMapping(ApiConstants.REQUERANT_SEARCH_COLLECTION)
@Validated
public class RequerantSearchResource {

	private static final Logger log = LoggerFactory.getLogger(RequerantSearchResource.class);

	@Autowired
	private RequerantService requerantService;

	@RequestMapping(name = ApiConstants.REQUERANT_SEARCH_COLLECTION, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SearchResultTO<RequerantReadTO> search(@QueryParam("limit") Integer limit, @QueryParam("page") Integer page,
			@RequestBody @Valid RequerantCriteria criteria) {
		log.info("find requerants by parameters");

		return requerantService.search(limit, page, criteria);

	}
}
