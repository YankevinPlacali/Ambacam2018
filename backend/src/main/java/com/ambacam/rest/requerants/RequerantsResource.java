package com.ambacam.rest.requerants;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ambacam.rest.ApiConstants;
import com.ambacam.service.RequerantService;
import com.ambacam.service.TokenService;
import com.ambacam.transfert.requerants.RequerantCreateTO;
import com.ambacam.transfert.requerants.RequerantReadTO;

@RestController
@RequestMapping(ApiConstants.REQUERANT_COLLECTION)
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
@Validated
public class RequerantsResource {
	private static final Logger log = LoggerFactory.getLogger(RequerantsResource.class);

	@Autowired
	private RequerantService requerantService;

	@Autowired
	private TokenService tokenService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequerantReadTO create(@RequestBody @Valid RequerantCreateTO requerantCreateTO) {
		log.info("create a requerant");

		// set the connected operateur as creator
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		requerantCreateTO.setCreatorId(tokenService.getConnectedOperateur(request).getId());

		// create
		RequerantReadTO requerantReadTO = requerantService.create(requerantCreateTO);
		return requerantReadTO;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RequerantReadTO> list() {
		log.info("list all requerants");

		return requerantService.list();

	}
}
