package com.ambacam.rest.statusrequetes;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambacam.model.StatusRequete;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.StatusRequeteService;

@RestController
@RequestMapping(ApiConstants.STATUS_REQUETE_ITEM)
public class StatusRequeteResource {

	private static final Logger log = LoggerFactory.getLogger(StatusRequeteResource.class);

	@Autowired
	private StatusRequeteService statusRequeteService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusRequete get(@PathVariable("statusRequeteId") Long statusRequeteId) {
		log.info("Get a statusRequete [id={}]", statusRequeteId);

		return statusRequeteService.get(statusRequeteId);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("statusRequeteId") Long statusRequeteId) {
		log.info("Delete a statusRequete [id={}]", statusRequeteId);

		statusRequeteService.delete(statusRequeteId);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("statusRequeteId") Long statusRequeteId,
			@RequestBody @Valid StatusRequete update) {
		log.info("Update a statusRequete [id={}]", statusRequeteId);

		statusRequeteService.update(statusRequeteId, update);
	}

}
