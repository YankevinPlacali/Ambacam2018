package com.ambacam.rest.autorites;

import com.ambacam.model.Autorite;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.AutoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping(ApiConstants.AUTORITE_COLLECTION)
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
@Validated
public class AutoritesResource {

	private static final Logger log = LoggerFactory.getLogger(AutoritesResource.class);

	@Autowired
	private AutoriteService autoriteService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Autorite create(@RequestBody @Valid Autorite autorite) {
		log.info("Create an autorite");
		// create
		Autorite created = autoriteService.create(autorite);
		return created;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Autorite> list() {
		log.info("List all autorites");

		return autoriteService.list();

	}
}
