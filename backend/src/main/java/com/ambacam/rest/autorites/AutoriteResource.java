package com.ambacam.rest.autorites;

import com.ambacam.model.Autorite;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.AutoriteService;
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
@RequestMapping(ApiConstants.AUTORITE_ITEM)
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
@Validated
public class AutoriteResource {

	private static final Logger log = LoggerFactory.getLogger(AutoriteResource.class);

	@Autowired
	private AutoriteService autoriteService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public Autorite get(@PathVariable("autoriteId") Long autoriteId) {
		log.info("Get an autorite [id={}]", autoriteId);

		return autoriteService.get(autoriteId);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("autoriteId") Long autoriteId) {
		log.info("Delete an autorite [id={}]", autoriteId);

		autoriteService.delete(autoriteId);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("autoriteId") Long autoriteId, @RequestBody @Valid Autorite autorite) {
		log.info("Update an autorite [id={}]", autoriteId);

		autoriteService.update(autoriteId, autorite);
	}

}
