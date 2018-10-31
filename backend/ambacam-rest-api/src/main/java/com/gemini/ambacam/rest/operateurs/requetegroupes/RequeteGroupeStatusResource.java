package com.gemini.ambacam.rest.operateurs.requetegroupes;

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
import com.gemini.ambacam.service.RequeteGroupeService;
import com.gemini.ambacam.transfert.IdTO;

@RestController
@RequestMapping(ApiConstants.OPERATEUR_REQUETE_GROUPE_ITEM_STATUS)
@Validated
public class RequeteGroupeStatusResource {

	private static final Logger log = LoggerFactory.getLogger(RequeteGroupeStatusResource.class);

	@Autowired
	private RequeteGroupeService requeteGroupeService;

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateStatus(@PathVariable("operateurId") Long operateurId,
			@PathVariable("requeteGroupeId") Long requeteGroupeId, @RequestBody @Valid IdTO<Long> requeteStatusTO) {
		log.info("Update status of a requeteGroupe [operateurId={}, requeteGroupeId={}]", operateurId, requeteGroupeId);

		requeteGroupeService.updateStatus(requeteGroupeId, requeteStatusTO);
	}
}
