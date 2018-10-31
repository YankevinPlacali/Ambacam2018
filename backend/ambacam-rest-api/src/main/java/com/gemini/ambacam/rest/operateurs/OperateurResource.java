package com.gemini.ambacam.rest.operateurs;

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
import com.gemini.ambacam.service.OperateurService;
import com.gemini.ambacam.transfert.operateurs.OperateurReadTO;
import com.gemini.ambacam.transfert.operateurs.OperateurUpdateTO;

@RestController
@RequestMapping(ApiConstants.OPERATEUR_ITEM)
@Validated
public class OperateurResource {

	private static final Logger log = LoggerFactory.getLogger(OperateurResource.class);

	@Autowired
	private OperateurService operateurService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public OperateurReadTO get(@PathVariable("operateurId") Long operateurId) {
		log.info("Get an operateur [id={}]", operateurId);

		return operateurService.get(operateurId);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("operateurId") Long operateurId) {
		log.info("Delete an operateur [id={}]", operateurId);

		operateurService.delete(operateurId);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("operateurId") Long operateurId,
			@RequestBody @Valid OperateurUpdateTO operateurUpdateTO) {
		log.info("Update an operateur [id={}]", operateurId);

		operateurService.update(operateurId, operateurUpdateTO);
	}

}
