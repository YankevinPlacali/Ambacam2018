package com.ambacam.rest.operateurs.requetes;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambacam.rest.ApiConstants;
import com.ambacam.service.RequeteService;
import com.ambacam.transfert.requetes.RequeteReadTO;

@RestController
@RequestMapping(ApiConstants.OPERATEUR_REQUETE_COLLECTION)
@CrossOrigin(origins = "${ambacam2018.app.settings.cross-origin}")
@Validated
public class OperateurRequetesResource {
    private static final Logger log = LoggerFactory.getLogger(OperateurRequetesResource.class);

    @Autowired
    private RequeteService requeteService;

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<RequeteReadTO> listByOperateur(
            @QueryParam("limit") Integer limit,
            @QueryParam("page") Integer page,
            @PathVariable("operateurId") Long operateurId) {
        log.info("List all requetes by operateurId [operateurId={}]", operateurId);

        return requeteService.listByOperateur(operateurId, limit, page);

    }

}
