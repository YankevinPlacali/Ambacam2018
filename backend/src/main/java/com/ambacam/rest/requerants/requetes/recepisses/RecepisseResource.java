package com.ambacam.rest.requerants.requetes.recepisses;

import com.ambacam.rest.ApiConstants;
import com.ambacam.service.RecepisseService;
import com.ambacam.transfert.recepisses.RecepisseReadTO;
import com.ambacam.transfert.recepisses.RecepisseUpdateTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping(ApiConstants.RECEPISSE_ITEM)
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
@Validated
public class RecepisseResource {
	private static final Logger log = LoggerFactory.getLogger(RecepisseResource.class);

	@Autowired
	private RecepisseService recepisseService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public RecepisseReadTO get(@PathVariable("requerantId") Long requerantId, @PathVariable("requeteId") Long requeteId,
			@PathVariable("recepisseId") Long recepisseId) {
		log.info("Get a recepisse [requerantId={}, requeteId={}, id={}]", requerantId, requeteId, recepisseId);

		return recepisseService.get(recepisseId);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("requerantId") Long requerantId, @PathVariable("requeteId") Long requeteId,
			@PathVariable("recepisseId") Long recepisseId) {
		log.info("delete a recepisse [requerantId={}, requeteId={}, id={}]", requerantId, requeteId, recepisseId);

		recepisseService.delete(recepisseId);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("requerantId") Long requerantId, @PathVariable("requeteId") Long requeteId,
			@PathVariable("recepisseId") Long recepisseId, @RequestBody @Valid RecepisseUpdateTO recepisseUpdateTO) {
		log.info("update a recepisse [requerantId={}, requeteId={}, id={}]", requerantId, requeteId, recepisseId);

		recepisseService.update(recepisseId, recepisseUpdateTO);
	}
}
