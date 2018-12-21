package com.ambacam.rest.requetes;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambacam.rest.ApiConstants;
import com.ambacam.service.RequeteService;
import com.ambacam.transfert.requetes.IdentifyRequerantTO;
import com.ambacam.transfert.requetes.RequeteReadTO;
import com.ambacam.transfert.requetes.RequeteStatusHistoryReadTO;

@RestController
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
@Validated
public class RequetesRootResource {

	private static final Logger log = LoggerFactory.getLogger(RequetesRootResource.class);

	@Autowired
	private RequeteService requeteService;

	@RequestMapping(path=ApiConstants.REQUETE_BATCH_COLLECTION, method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RequeteReadTO> listBatch() {
		log.info("List all requetes");
		return requeteService.listBatch();
	}

	@RequestMapping(path=ApiConstants.REQUETE_HISTORY_COLLECTION, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RequeteStatusHistoryReadTO> listRequerantByDateNaissanceAndIdentifier(
			@RequestBody @Valid IdentifyRequerantTO identifyRequerantTO) {
		log.info("List requetes history from the requerant side [identifier={}, dateNaissance={}]", identifyRequerantTO.getIdentifier(),
				identifyRequerantTO.getDateNaissance());

		return requeteService.listRequerantByDateNaissanceAndIdentifier(identifyRequerantTO);

	}
}
