package com.ambacam.rest.actions;

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

import com.ambacam.model.Action;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.ActionService;

@RestController
@RequestMapping(ApiConstants.ACTION_ITEM)
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
public class ActionResource {

	private static final Logger log = LoggerFactory.getLogger(ActionResource.class);

	@Autowired
	private ActionService actionService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public Action get(@PathVariable("actionId") Long actionId) {
		log.info("Get an action [id={}]", actionId);

		return actionService.get(actionId);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("actionId") Long actionId) {
		log.info("Delete an action [id={}]", actionId);

		actionService.delete(actionId);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("actionId") Long actionId, @RequestBody @Valid Action action) {
		log.info("Update an action [id={}]", actionId);

		actionService.update(actionId, action);
	}

}
