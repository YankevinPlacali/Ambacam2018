package com.gemini.ambacam.rest.operateurs.requerants.requetes;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gemini.ambacam.rest.ApiConstants;
import com.gemini.ambacam.service.RequeteService;
import com.gemini.ambacam.transfert.requetes.RequeteReadTO;
import com.gemini.ambacam.transfert.requetes.RequeteTO;

@RestController
@RequestMapping(ApiConstants.OPERATEUR_REQUERANT_REQUETE_COLLECTION)
@Validated
public class RequetesResource {
	private static final Logger log = LoggerFactory.getLogger(RequetesResource.class);

	@Autowired
	private RequeteService requeteService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequeteReadTO create(@PathVariable("operateurId") Long operateurId,
			@PathVariable("requerantId") Long requerantId, @RequestBody @Valid RequeteTO createTO) {
		log.info("create a requete");
		// create
		RequeteReadTO requeteReadTO = requeteService.create(operateurId, requerantId, createTO);
		return requeteReadTO;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RequeteReadTO> listByOperateurAndRequerant(@QueryParam("limit") Integer limit,
			@QueryParam("page") Integer page, @PathVariable("operateurId") Long operateurId,
			@PathVariable("requerantId") Long requerantId) {
		log.info("List all requetes by operateurId and requerantId [operateurId={}, requerantId={}]", operateurId,
				requerantId);

		return requeteService.listByOperateurAndRequerant(operateurId, requerantId, limit, page);

	}
}
