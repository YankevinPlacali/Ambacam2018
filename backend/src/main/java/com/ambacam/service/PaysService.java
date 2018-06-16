package com.ambacam.service;

import com.ambacam.exception.ResourceBadRequestException;
import com.ambacam.exception.ResourceNotFoundException;
import com.ambacam.model.Country;
import com.ambacam.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    /**
     * Create a country
     *
     * @param country
     *
     * @return Country
     * @throws ResourceBadRequestException if a country with the name already exist
     */
    public Country create(Country country){

        checkConsistency(country);

        country.setId(null);

        return countryRepository.save(country);
    }

    /**
     * Get a country
     *
     * @param id
     *
     * @return Country
     *
     * @throws ResourceNotFoundException if the country does not exist
     */
    public Country get(long id) {
        return findCountry(id);
    }

    /**
     * List all roles
     *
     * @return
     */
    public List<Country> list(){

        return  countryRepository.findAll();
    }


    /**
     * Update a country
     *
     * @param id
     *
     * @param update
     *
     * @return Country
     *
     * @throws ResourceNotFoundException if the country is not found
     * @throws ResourceBadRequestException if a country with the name already exist
     */
    public Country update(Long id, Country update){

        Country country = findCountry(id);

        if (!country.getName().equals(update.getName())){
            checkConsistency(update);
        }

        update.setId(id);
        return  countryRepository.save(update);
    }


    /**
     * Delete a country
     *
     * @param id
     *
     * @return
     *
     * @throws ResourceNotFoundException if the country is not found
     */
    public void delete(Long id) {
        //find country
        findCountry(id);
        countryRepository.delete(id);
    }


    private Country findCountry(Long id) {

        //find country
        Country country = countryRepository.findOne(id);
        if (country == null){
            throw new ResourceNotFoundException(
                    "The country " + id.toString() + " does not exist"
            );
        }
        return country;
    }



    private void checkConsistency(Country country) {

        if (countryRepository.countByName(country.getName()) > 0){

            throw new ResourceBadRequestException(
                    String.format("A country with a name '%s' exist already", country.getName())
            );
        }
    }
}
