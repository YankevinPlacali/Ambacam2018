package com.gemini.ambacam.rest.operateurs.requetegroupes;

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
import com.gemini.ambacam.service.RequeteGroupeService;
import com.gemini.ambacam.transfert.requetegroupes.RequeteGroupeReadTO;
import com.gemini.ambacam.transfert.requetegroupes.RequeteGroupeUpdateTO;

@RestController
@RequestMapping(ApiConstants.OPERATEUR_REQUETE_GROUPE_ITEM)
@Validated
public class RequeteGroupeResource {

	private static final Logger log = LoggerFactory.getLogger(RequeteGroupeResource.class);

	@Autowired
	private RequeteGroupeService requeteGroupeService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public RequeteGroupeReadTO get(@PathVariable("operateurId") Long operateurId,
			@PathVariable("requeteGroupeId") Long requeteGroupeId) {
		log.info("Get a requeteGroupe [operateurId={}, requeteGroupeId={}]", operateurId, requeteGroupeId);

		return requeteGroupeService.get(requeteGroupeId);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("operateurId") Long operateurId,
			@PathVariable("requeteGroupeId") Long requeteGroupeId) {
		log.info("Delete a requeteGroupe [operateurId={}, requeteGroupeId={}]", operateurId, requeteGroupeId);

		requeteGroupeService.delete(requeteGroupeId);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("operateurId") Long operateurId,
			@PathVariable("requeteGroupeId") Long requeteGroupeId,
			@RequestBody @Valid RequeteGroupeUpdateTO requeteGroupeUpdateTO) {
		log.info("Update a requeteGroupe [operateurId={}, requeteGroupeId={}]", operateurId, requeteGroupeId);

		requeteGroupeService.update(requeteGroupeId, requeteGroupeUpdateTO);
	}

}
