package com.gemini.ambacam.rest.operateurs.requetegroupes;

import java.util.List;

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
import com.gemini.ambacam.transfert.requetegroupes.RequeteGroupeCreateTO;
import com.gemini.ambacam.transfert.requetegroupes.RequeteGroupeReadTO;

@RestController
@RequestMapping(ApiConstants.OPERATEUR_REQUETE_GROUPE_COLLECTION)
@Validated
public class RequeteGroupesResource {

	private static final Logger log = LoggerFactory.getLogger(RequeteGroupesResource.class);

	@Autowired
	private RequeteGroupeService requeteGroupeService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequeteGroupeReadTO create(@PathVariable("operateurId") Long operateurId,
			@RequestBody @Valid RequeteGroupeCreateTO requeteGroupeCreateTO) {
		log.info("create a requeteGroupe [operateurId={}]", operateurId);

		// create
		return requeteGroupeService.create(operateurId, requeteGroupeCreateTO);
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RequeteGroupeReadTO> list(@PathVariable("operateurId") Long operateurId) {
		log.info("list all requeteGroupes [operateurId={}]", operateurId);

		return requeteGroupeService.list();

	}
}
