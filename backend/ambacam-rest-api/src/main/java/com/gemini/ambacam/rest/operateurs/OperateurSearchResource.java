package com.gemini.ambacam.rest.operateurs;

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

import com.gemini.ambacam.rest.ApiConstants;
import com.gemini.ambacam.search.operateurs.OperateurCriteria;
import com.gemini.ambacam.service.OperateurService;
import com.gemini.ambacam.transfert.SearchResultTO;
import com.gemini.ambacam.transfert.operateurs.OperateurReadTO;

@RestController
@RequestMapping(ApiConstants.OPERATEUR_SEARCH_COLLECTION)
@Validated
public class OperateurSearchResource {

	private static final Logger log = LoggerFactory.getLogger(OperateurSearchResource.class);

	@Autowired
	private OperateurService operateurService;

	@RequestMapping(name = ApiConstants.OPERATEUR_SEARCH_COLLECTION, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SearchResultTO<OperateurReadTO> search(@QueryParam("limit") Integer limit, @QueryParam("page") Integer page,
			@RequestBody @Valid OperateurCriteria criteria) {
		log.info("find operateurs by parameters");

		return operateurService.search(limit, page, criteria);

	}
}
