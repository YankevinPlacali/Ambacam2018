package com.ambacam.rest.operateurs.requerants.requetes.passports;

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

import com.ambacam.model.Passport;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.PassportService;
import com.ambacam.transfert.passports.PassportCreateTO;
import com.ambacam.transfert.passports.PassportReadTO;

@RestController
@RequestMapping(ApiConstants.PASSPORT_COLLECTION)
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
@Validated
public class PassportsResource {
	private static final Logger log = LoggerFactory.getLogger(PassportsResource.class);

	@Autowired
	private PassportService passportService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Passport create(@PathVariable("operateurId") Long operateurId, @PathVariable("requerantId") Long requerantId,
			@PathVariable("requeteId") Long requeteId, @RequestBody @Valid PassportCreateTO createTO) {
		log.info("create a passport [operateurId={}, requerantId={}, requeteId={}] ", operateurId, requerantId,
				requeteId);
		// create
		Passport passport = passportService.create(requeteId, createTO);
		return passport;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<PassportReadTO> listByOperateurAndRequerant(@PathVariable("operateurId") Long operateurId,
			@PathVariable("requerantId") Long requerantId, @PathVariable("requeteId") Long requeteId) {
		log.info("List all passports by requeteId [operateurId={}, requerantId={}, requeteId={}] ", operateurId,
				requerantId, requeteId);

		return passportService.listByRequete(requeteId);

	}
}
