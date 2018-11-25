package com.ambacam.rest.operateurs.requetegroupes.requetes;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambacam.rest.ApiConstants;
import com.ambacam.service.RequeteService;
import com.ambacam.transfert.requetes.AssignStatusTO;
import com.ambacam.transfert.requetes.RequeteReadTO;

@RestController
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
@Validated
public class GroupeRequeteRequetesResource {
	private static final Logger log = LoggerFactory.getLogger(GroupeRequeteRequetesResource.class);

	@Autowired
	private RequeteService requeteService;

	@RequestMapping(path = ApiConstants.OPERATEUR_REQUETE_GROUPE_ITEM_REQUETE_COLLECTION, method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RequeteReadTO> listRequetes(@PathVariable("operateurId") Long operateurId,
			@PathVariable("requeteGroupeId") Long requeteGroupeId) {
		log.info("Get all requetes of a single requete groupe [operateurId={}, requeteGroupeId={}]", operateurId,
				requeteGroupeId);

		return requeteService.listRequetes(requeteGroupeId);
	}

	@RequestMapping(path = ApiConstants.REQUETE_GROUPE_REQUETE_STATUS, method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("operateurId") Long operateurId,
			@PathVariable("requeteGroupeId") Long requeteGroupeId, @RequestBody @Valid AssignStatusTO assignStatusTO) {
		log.info("Update status of some Requetes in a RequeteGroupe [operateurId={}, requeteGroupeId={}]", operateurId,
				requeteGroupeId);

		requeteService.assignStatus(requeteGroupeId, assignStatusTO);
	}
}
