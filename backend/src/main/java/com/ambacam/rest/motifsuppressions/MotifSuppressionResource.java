package com.ambacam.rest.motifsuppressions;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambacam.model.MotifSuppression;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.MotifSuppressionService;

@RestController
@RequestMapping(ApiConstants.MOTIF_SUPPRESSION_ITEM)
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
public class MotifSuppressionResource {

	private static final Logger log = LoggerFactory.getLogger(MotifSuppressionResource.class);

	@Autowired
	private MotifSuppressionService motifSuppressionService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public MotifSuppression get(@PathVariable("motifSuppressionId") Long motifSuppressionId) {
		log.info("Get a motifSuppression [id={}]", motifSuppressionId);

		return motifSuppressionService.get(motifSuppressionId);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("motifSuppressionId") Long motifSuppressionId) {
		log.info("Delete a motifSuppression [id={}]", motifSuppressionId);

		motifSuppressionService.delete(motifSuppressionId);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("motifSuppressionId") Long motifSuppressionId,
			@RequestBody @Valid MotifSuppression update) {
		log.info("Update a motifSuppression [id={}]", motifSuppressionId);

		motifSuppressionService.update(motifSuppressionId, update);
	}
}
