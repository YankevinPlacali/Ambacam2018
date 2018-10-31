package com.gemini.ambacam.rest.pays;

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

import com.gemini.ambacam.model.Pays;
import com.gemini.ambacam.rest.ApiConstants;
import com.gemini.ambacam.service.PaysService;

@RestController
@RequestMapping(ApiConstants.PAYS_ITEM)
@Validated
public class PaysResource {

	private static final Logger log = LoggerFactory.getLogger(PaysResource.class);

	@Autowired
	private PaysService paysService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public Pays get(@PathVariable("paysId") Long paysId) {
		log.info("Get a pays [id={}]", paysId);

		return paysService.get(paysId);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("paysId") Long paysId) {
		log.info("Delete a pays [id={}]", paysId);

		paysService.delete(paysId);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("paysId") Long paysId, @RequestBody @Valid Pays pays) {
		log.info("Update a pays [id={}]", paysId);

		paysService.update(paysId, pays);
	}

}
