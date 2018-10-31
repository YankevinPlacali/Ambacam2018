package com.gemini.ambacam.rest.operateurs.requerants.requetes;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
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
@RequestMapping(ApiConstants.OPERATEUR_REQUERANT_REQUETE_ITEM)
@Validated
public class RequeteResource {
	private static final Logger log = LoggerFactory.getLogger(RequeteResource.class);

	@Autowired
	private RequeteService requeteService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public RequeteReadTO get(@PathVariable("operateurId") Long operateurId,
			@PathVariable("requerantId") Long requerantId, @PathVariable("requeteId") Long requeteId) {
		log.info("Get a requete [operateurId={}, requerantId={}, requeteId={}]", operateurId, requerantId, requeteId);

		return requeteService.get(requeteId);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("operateurId") Long operateurId, @PathVariable("requerantId") Long requerantId,
			@PathVariable("requeteId") Long requeteId) {
		log.info("Delete a requete [operateurId={}, requerantId={}, requeteId={}]", operateurId, requerantId,
				requeteId);

		requeteService.delete(requeteId);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("operateurId") Long operateurId, @PathVariable("requerantId") Long requerantId,
			@PathVariable("requeteId") Long requeteId, @RequestBody @Valid RequeteTO requeteUpdateTO) {
		log.info("Update a requete [operateurId={}, requerantId={}, requeteId={}]", operateurId, requerantId,
				requeteId);

		requeteService.update(requeteId, requeteUpdateTO);
	}

}
