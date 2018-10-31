package com.gemini.ambacam.rest.roles;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.gemini.ambacam.model.Role;
import com.gemini.ambacam.rest.ApiConstants;
import com.gemini.ambacam.service.RoleService;

@RestController
@RequestMapping(ApiConstants.ROLE_COLLECTION)
@Validated
public class RolesResource {

	private static final Logger log = LoggerFactory.getLogger(RolesResource.class);

	@Autowired
	private RoleService roleService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Role create(HttpServletRequest request, @RequestBody @Valid Role role) {
		log.info("Create a role");
		// create
		Role created = roleService.create(role);
		return created;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Role> list() {
		log.info("List all roles");

		return roleService.list();

	}
}
