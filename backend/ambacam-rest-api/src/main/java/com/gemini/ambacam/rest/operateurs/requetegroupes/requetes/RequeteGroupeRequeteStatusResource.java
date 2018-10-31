package com.gemini.ambacam.rest.operateurs.requetegroupes.requetes;

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
import com.gemini.ambacam.transfert.requetes.AssignStatusTO;

@RestController
@RequestMapping(ApiConstants.REQUETE_GROUPE_REQUETE_STATUS)
@Validated
public class RequeteGroupeRequeteStatusResource {
	private static final Logger log = LoggerFactory.getLogger(RequeteGroupeRequeteStatusResource.class);

	@Autowired
	private RequeteService requeteService;

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("operateurId") Long operateurId,
			@PathVariable("requeteGroupeId") Long requeteGroupeId, @RequestBody @Valid AssignStatusTO assignStatusTO) {
		log.info("Update status of some Requetes in a RequeteGroupe [operateurId={}, requeteGroupeId={}]", operateurId,
				requeteGroupeId);

		requeteService.assignStatus(requeteGroupeId, assignStatusTO);
	}
}
