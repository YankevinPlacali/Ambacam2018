package com.ambacam.rest.motifsuppressions;

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

import com.ambacam.model.MotifSuppression;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.MotifSuppressionService;

@RestController
@RequestMapping(ApiConstants.MOTIF_SUPPRESSION_COLLECTION)
@Validated
public class MotifSuppressionsResource {

	private static final Logger log = LoggerFactory.getLogger(MotifSuppressionsResource.class);

	@Autowired
	private MotifSuppressionService motifSuppressionService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MotifSuppression create(@RequestBody @Valid MotifSuppression motifSuppression) {
		log.info("Create a motifSuppression");
		// create
		MotifSuppression created = motifSuppressionService.create(motifSuppression);
		return created;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<MotifSuppression> list() {
		log.info("List all motifSuppressions");

		return motifSuppressionService.list();

	}

}
