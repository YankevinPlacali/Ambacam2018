package com.ambacam.rest.requerants;

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
import com.ambacam.service.RequerantService;
import com.ambacam.transfert.requerants.RequerantReadTO;
import com.ambacam.transfert.requerants.RequerantUpdateTO;

@RestController
@RequestMapping(ApiConstants.REQUERANT_ITEM)
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
@Validated
public class RequerantResource {
	private static final Logger log = LoggerFactory.getLogger(RequerantResource.class);

	@Autowired
	private RequerantService requerantService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public RequerantReadTO get(@PathVariable("requerantId") Long requerantId) {
		log.info("Get a requerant [id={}]", requerantId);

		return requerantService.get(requerantId);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("requerantId") Long requerantId) {
		log.info("Delete a requerant [id={}]", requerantId);

		requerantService.delete(requerantId);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("requerantId") Long requerantId,
			@RequestBody @Valid RequerantUpdateTO requerantUpdateTO) {
		log.info("Update a requerant [id={}]", requerantId);

		requerantService.update(requerantId, requerantUpdateTO);
	}
}
