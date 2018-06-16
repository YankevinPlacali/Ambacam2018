package com.ambacam.rest.Pays;

import com.ambacam.model.Country;
import com.ambacam.rest.ApiConstants;
import com.ambacam.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping(ApiConstants.PAYS_ITEM)
public class CountryResource {

    private static final Logger log = LoggerFactory.getLogger(CountryResource.class);

    @Autowired
    private CountryService countryService;


    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Country get(@PathVariable("countryId") Long countryId){
        log.info("Get a country [id={}]", countryId);

       return countryService.get(countryId);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("countryId") Long countryId) {
        log.info("Delete a country [id={}]", countryId);

        countryService.delete(countryId);
    }

    public void update(@PathVariable("countryId") Long countryId, @RequestBody @Valid Country upsate){
        log.info("Update a country [id={}]", countryId);

        countryService.update(countryId, upsate);
    }

}
