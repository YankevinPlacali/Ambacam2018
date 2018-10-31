package com.gemini.ambacam.rest.operateurs.requerants.requetes;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
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
import com.gemini.ambacam.transfert.requetes.RequeteStatusTO;

@RestController
@RequestMapping(ApiConstants.OPERATEUR_REQUERANT_REQUETE_ITEM_STATUS)
@Validated
public class RequeteStatusResource {

	private static final Logger log = LoggerFactory.getLogger(RequeteStatusResource.class);

	@Autowired
	private RequeteService requeteService;

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateStatus(@PathVariable("operateurId") Long operateurId,
			@PathVariable("requerantId") Long requerantId, @PathVariable("requeteId") Long requeteId,
			@RequestBody @Valid RequeteStatusTO requeteStatusTO) {

		log.info("Update a requete [operateurId={}, requerantId={}, requeteId={}]", operateurId, requerantId,
				requeteId);
		requeteService.updateStatus(requeteId, requeteStatusTO);
	}

}