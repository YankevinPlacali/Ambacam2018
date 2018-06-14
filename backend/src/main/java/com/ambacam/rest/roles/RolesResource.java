package com.ambacam.rest.roles;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambacam.model.Role;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.RoleService;

@RestController
@RequestMapping(ApiConstants.ROLE_COLLECTION)
@Validated
public class RolesResource {

	private static final Logger log = LoggerFactory.getLogger(RolesResource.class);

	@Autowired
	private RoleService roleService;
	@Autowired
	RoleResource roleResource;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Role create(@RequestBody @Valid Role entity) {
		log.info("Create a role");
		log.debug(String.format("{}", entity.getNom()));
		// create
		Role created = roleService.create(entity);
		return created;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Role> list() {
		log.info("List all roles");

		return roleService.list();

	}
}
