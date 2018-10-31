package com.gemini.ambacam.rest.operateurs.requerants.requetes.passports;

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
import com.gemini.ambacam.service.PassportService;
import com.gemini.ambacam.transfert.passports.PassportCreateTO;
import com.gemini.ambacam.transfert.passports.PassportReadTO;

@RestController
@RequestMapping(ApiConstants.PASSPORT_ITEM)
@Validated
public class PassportResource {
	private static final Logger log = LoggerFactory.getLogger(PassportResource.class);

	@Autowired
	private PassportService passportService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public PassportReadTO get(@PathVariable("operateurId") Long operateurId,
			@PathVariable("requerantId") Long requerantId, @PathVariable("requeteId") Long requeteId,
			@PathVariable("passportId") Long passportId) {
		log.info("Get a passport [operateurId={}, requerantId={}, requeteId={}, passportId={}]", operateurId,
				requerantId, requeteId, passportId);

		return passportService.get(passportId);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("operateurId") Long operateurId, @PathVariable("requerantId") Long requerantId,
			@PathVariable("requeteId") Long requeteId, @PathVariable("passportId") Long passportId) {
		log.info("Delete a passport [operateurId={}, requerantId={}, requeteId={}, passportId={}]", operateurId,
				requerantId, requeteId, passportId);

		passportService.delete(passportId);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("operateurId") Long operateurId, @PathVariable("requerantId") Long requerantId,
			@PathVariable("requeteId") Long requeteId, @PathVariable("passportId") Long passportId,
			@RequestBody @Valid PassportCreateTO passportUpdateTO) {
		log.info("Update a passport [operateurId={}, requerantId={}, requeteId={}, passportId={}]", operateurId,
				requerantId, requeteId, passportId);

		passportService.update(passportId, passportUpdateTO);
	}
}
