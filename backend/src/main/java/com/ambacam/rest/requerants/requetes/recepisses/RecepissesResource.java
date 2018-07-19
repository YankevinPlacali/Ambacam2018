package com.ambacam.rest.requerants.requetes.recepisses;

import com.ambacam.rest.ApiConstants;
import com.ambacam.service.RecepisseService;
import com.ambacam.transfert.recepisses.RecepisseCreateTO;
import com.ambacam.transfert.recepisses.RecepisseReadTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping(ApiConstants.RECEPISSE_COLLECTION)
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
@Validated
public class RecepissesResource {
	private static final Logger log = LoggerFactory.getLogger(RecepissesResource.class);

	@Autowired
	private RecepisseService recepisseService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RecepisseReadTO create(@PathVariable("requerantId") Long requerantId,
			@PathVariable("requeteId") Long requeteId, @RequestBody @Valid RecepisseCreateTO recepisseCreateTO) {

		log.info("create a recepisse [requerantId={}, requeteId={}]", requerantId, requeteId);
		// create
		RecepisseReadTO recepisseReadTO = recepisseService.create(requeteId, recepisseCreateTO);
		return recepisseReadTO;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RecepisseReadTO> list() {
		log.info("list all recepisses");

		return recepisseService.list();

	}
}
