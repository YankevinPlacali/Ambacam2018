package com.gemini.ambacam.rest.requerants.requetes.recepisses;

import java.util.List;

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
import com.gemini.ambacam.service.RecepisseService;
import com.gemini.ambacam.transfert.recepisses.RecepisseCreateTO;
import com.gemini.ambacam.transfert.recepisses.RecepisseReadTO;

@RestController
@RequestMapping(ApiConstants.RECEPISSE_COLLECTION)
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
