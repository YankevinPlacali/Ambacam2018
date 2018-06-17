package com.ambacam.rest.Pays;


import com.ambacam.model.Pays;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.PaysService;
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
@RequestMapping(ApiConstants.PAYS_COLLECTION)
@Validated
public class PayssResource {

    private static final Logger log = LoggerFactory.getLogger(PayssResource.class);

    @Autowired
    private PaysService paysService;

    @Autowired
    PaysResource paysResource;


    @RequestMapping(method = RequestMethod.POST)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Pays create(@RequestBody @Valid Pays entity){

        log.info("Create a country");
        log.debug(String.format("{}", entity.getNom()));

        Pays created = paysService.create(entity);
        return created;
    }


    public List<Pays> list(){

        log.info("List all Countries");

        return paysService.list();
    }
}
