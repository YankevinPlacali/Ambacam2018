package com.ambacam.rest.roles;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambacam.model.Role;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.RoleService;

@RestController
@RequestMapping(ApiConstants.ROLE_ITEM)
public class RoleResource {

	private static final Logger log = LoggerFactory.getLogger(RoleResource.class);

	@Autowired
	private RoleService roleService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public Role get(@PathVariable("roleId") Long roleId) {
		log.info("Get a role [id={}]", roleId);

		return roleService.get(roleId);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("roleId") Long roleId) {
		log.info("Delete a role [id={}]", roleId);

		roleService.delete(roleId);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathVariable("roleId") Long roleId, @RequestBody @Valid Role update) {
		log.info("Update a role [id={}]", roleId);

		roleService.update(roleId, update);
	}

}
