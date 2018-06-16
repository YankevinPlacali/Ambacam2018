package com.ambacam.rest.actions;

import com.ambacam.model.Action;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.ActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping(ApiConstants.ACTION_COLLECTION)
@Validated
public class ActionsResource {

	private static final Logger log = LoggerFactory.getLogger(ActionsResource.class);

	@Autowired
	private ActionService actionService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Action create(@RequestBody @Valid Action action) {
		log.info("Create an action");
		// create
		Action created = actionService.create(action);
		return created;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Action> list() {
		log.info("List all actions");

		return actionService.list();

	}
}
