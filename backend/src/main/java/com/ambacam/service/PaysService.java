package com.ambacam.service;

import com.ambacam.exception.ResourceBadRequestException;
import com.ambacam.exception.ResourceNotFoundException;
import com.ambacam.model.Pays;
import com.ambacam.repository.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PaysService {

    @Autowired
    PaysRepository paysRepository;

    /**
     * Create a pays
     *
     * @param pays
     *
     * @return Pays
     * @throws ResourceBadRequestException if a pays with the nom already exist
     */
    public Pays create(Pays pays){

        checkConsistency(pays);

        pays.setId(null);

        return paysRepository.save(pays);
    }

    /**
     * Get a pays
     *
     * @param id
     *
     * @return Pays
     *
     * @throws ResourceNotFoundException if the pays does not exist
     */
    public Pays get(long id) {
        return findPays(id);
    }

    /**
     * List all pays
     *
     * @return
     */
    public List<Pays> list(){

        return  paysRepository.findAll();
    }


    /**
     * Update a pays
     *
     * @param id
     *
     * @param update
     *
     * @return Pays
     *
     * @throws ResourceNotFoundException if the pays is not found
     * @throws ResourceBadRequestException if a pays with the nom already exist
     */
    public Pays update(Long id, Pays update){

        Pays pays = findPays(id);

        if (!pays.getNom().equals(update.getNom())){
            checkConsistency(update);
        }

        update.setId(id);
        return  paysRepository.save(update);
    }


    /**
     * Delete a pays
     *
     * @param id
     *
     * @return
     *
     * @throws ResourceNotFoundException if the pays is not found
     */
    public void delete(Long id) {
        //find pays
        findPays(id);
        paysRepository.delete(id);
    }


    private Pays findPays(Long id) {

        //find pays
        Pays pays = paysRepository.findOne(id);
        if (pays == null){
            throw new ResourceNotFoundException(
                    "The pays " + id.toString() + " does not exist"
            );
        }
        return pays;
    }



    private void checkConsistency(Pays pays) {

        if (paysRepository.countByNom(pays.getNom()) > 0){

            throw new ResourceBadRequestException(
                    String.format("A pays with a nom '%s' exist already", pays.getNom())
            );
        }
    }
}
