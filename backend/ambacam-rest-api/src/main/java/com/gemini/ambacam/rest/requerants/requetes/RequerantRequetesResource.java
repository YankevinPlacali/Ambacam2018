package com.gemini.ambacam.rest.requerants.requetes;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gemini.ambacam.rest.ApiConstants;
import com.gemini.ambacam.service.RequeteService;
import com.gemini.ambacam.transfert.requetes.RequeteReadTO;

@RestController
@RequestMapping(ApiConstants.REQUERANT_REQUETE_COLLECTION)
@Validated
public class RequerantRequetesResource {
	private static final Logger log = LoggerFactory.getLogger(RequerantRequetesResource.class);

	@Autowired
	private RequeteService requeteService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RequeteReadTO> listByRequerant(@QueryParam("limit") Integer limit, @QueryParam("page") Integer page,
			@PathVariable("requerantId") Long requerantId) {
		log.info("List all requetes by requerantId [ requerantId={}]", requerantId);

		return requeteService.listByRequerant(requerantId, limit, page);

	}
}
