package com.ambacam.rest.statusrequetes;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambacam.model.StatusRequete;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.StatusRequeteService;

@RestController
@RequestMapping(ApiConstants.STATUS_REQUETE_COLLECTION)
@Validated
public class StatusRequetesResource {

	private static final Logger log = LoggerFactory.getLogger(StatusRequetesResource.class);

	@Autowired
	private StatusRequeteService statusRequeteService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusRequete create(@RequestBody @Valid StatusRequete statusRequete) {
		log.info("Create a statusRequete");
		// create
		StatusRequete created = statusRequeteService.create(statusRequete);
		return created;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatusRequete> list() {
		log.info("List all statusRequetes");

		return statusRequeteService.list();

	}
}
