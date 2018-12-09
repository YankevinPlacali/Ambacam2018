package com.ambacam.rest.operateurs;

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
import com.ambacam.service.OperateurService;
import com.ambacam.service.TokenService;
import com.ambacam.transfert.operateurs.OperateurCreateTO;
import com.ambacam.transfert.operateurs.OperateurReadTO;

@RestController
@RequestMapping(ApiConstants.OPERATEUR_COLLECTION)
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
@Validated
public class OperateursResource {

	private static final Logger log = LoggerFactory.getLogger(OperateursResource.class);

	@Autowired
	private OperateurService operateurService;

	@Autowired
	private TokenService tokenService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperateurReadTO create(@RequestBody @Valid OperateurCreateTO operateurCreateTO) {
		log.info("create an operateur");

		// set the connected operateur as creator
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		operateurCreateTO.setCreatorId(tokenService.getConnectedOperateur(request).getId());

		// create
		OperateurReadTO operateurReadTO = operateurService.create(operateurCreateTO);
		return operateurReadTO;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<OperateurReadTO> list() {
		log.info("list all operateurs");

		return operateurService.list();

	}
}
