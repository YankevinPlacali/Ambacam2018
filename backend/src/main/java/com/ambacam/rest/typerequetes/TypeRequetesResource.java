package com.ambacam.rest.typerequetes;

import com.ambacam.model.TypeRequete;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.TypeRequeteService;
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
@RequestMapping(ApiConstants.TYPE_REQUETE_COLLECTION)
@Validated
public class TypeRequetesResource {

	private static final Logger log = LoggerFactory.getLogger(TypeRequetesResource.class);

	@Autowired
	private TypeRequeteService typeRequeteService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TypeRequete create(@RequestBody @Valid TypeRequete typeRequete) {
		log.info("Create a typeRequete");
		// create
		TypeRequete created = typeRequeteService.create(typeRequete);
		return created;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TypeRequete> list() {
		log.info("List all typeRequetes");

		return typeRequeteService.list();

	}
}
