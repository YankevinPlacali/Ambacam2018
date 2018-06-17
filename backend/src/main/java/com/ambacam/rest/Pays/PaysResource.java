package com.ambacam.rest.Pays;

import com.ambacam.model.Pays;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.PaysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping(ApiConstants.PAYS_ITEM)
public class PaysResource {

    private static final Logger log = LoggerFactory.getLogger(PaysResource.class);

    @Autowired
    private PaysService paysService;


    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Pays get(@PathVariable("paysId") Long paysId){
        log.info("Get a pays [id={}]", paysId);

       return paysService.get(paysId);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("paysId") Long paysId) {
        log.info("Delete a pays [id={}]", paysId);

        paysService.delete(paysId);
    }

    public void update(@PathVariable("paysId") Long paysId, @RequestBody @Valid Pays update){
        log.info("Update a pays [id={}]", paysId);

        paysService.update(paysId, update);
    }

}
