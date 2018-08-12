package com.ambacam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambacam.exception.ResourceBadRequestException;
import com.ambacam.exception.ResourceNotFoundException;
import com.ambacam.model.Operateur;
import com.ambacam.model.Requete;
import com.ambacam.model.RequeteGroupe;
import com.ambacam.model.StatusRequete;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.RequeteGroupeRepository;
import com.ambacam.repository.RequeteRepository;
import com.ambacam.repository.StatusRequeteRepository;
import com.ambacam.transfert.IdTO;
import com.ambacam.transfert.requetegroupes.RequeteGroupe2RequeteGroupeReadTO;
import com.ambacam.transfert.requetegroupes.RequeteGroupeCreateTO;
import com.ambacam.transfert.requetegroupes.RequeteGroupeReadTO;
import com.ambacam.transfert.requetegroupes.RequeteGroupeUpdateTO;
import com.ambacam.utils.RequeteGroupeUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class RequeteGroupeService {
    @Autowired
    private RequeteGroupeRepository requeteGroupeRepository;

    @Autowired
    private OperateurRepository operateurRepository;

    @Autowired
    private StatusRequeteRepository statusRequeteRepository;

    @Autowired
    private RequeteRepository requeteRepository;

    /**
     * Create a requeteGroupe
     * 
     * @param operateurId
     *            the operateur id which creates the requete groupe
     * @param requeteGroupeCreateTO
     *            The new requeteGroupe to create
     * 
     * @return The new requeteGroupe created
     * @throws ResourceNotFoundException
     *             if the operateur id does not exist
     */
    public RequeteGroupeReadTO create(Long operateurId, RequeteGroupeCreateTO requeteGroupeCreateTO) {

        // find operateur
        Operateur operateur = findOperateur(operateurId);

        RequeteGroupe requeteGroupe = new RequeteGroupe();

        // set properties
        requeteGroupe.setId(null);
        requeteGroupe.setDescription(requeteGroupeCreateTO.getDescription());
        requeteGroupe.setCreePar(operateur);

        // generate the name
        requeteGroupe.setNom(RequeteGroupeUtils.generateRequeteGroupeName());

        // save the requeteGroupe
        return RequeteGroupe2RequeteGroupeReadTO.apply(requeteGroupeRepository.save(requeteGroupe));
    }

    /**
     * Get a requeteGroupe
     * 
     * @param id
     *            The id of the RequeteGroupe which should be retrieved
     * 
     * @return The requeteGroupe found
     * 
     * @throws ResourceNotFoundException
     *             if the requeteGroupe does not exist
     */
    public RequeteGroupeReadTO get(Long id) {
        return RequeteGroupe2RequeteGroupeReadTO.apply(findRequeteGroupe(id));
    }

    /**
     * List all requeteGroupes
     * 
     * @return The list of requeteGroupe read stored
     */
    public List<RequeteGroupeReadTO> list() {

        return requeteGroupeRepository.findAll().stream().map(rg -> RequeteGroupe2RequeteGroupeReadTO.apply(rg))
                .collect(Collectors.toList());
    }

    /**
     * Update a requeteGroupe
     * 
     * @param id
     *            The id of the requeteGroupeUpdate to update
     * 
     * @param update
     *            The new requeteGroupe modifications
     * 
     * @return The requeteGroupeUpdateTO read updated
     * 
     * @throws ResourceNotFoundException
     *             if the requeteGroupe is not found
     * 
     */
    public RequeteGroupeReadTO update(Long id, RequeteGroupeUpdateTO requeteGroupeUpdateTO) {

        // find requeteGroupe
        RequeteGroupe found = findRequeteGroupe(id);

        // update properties
        found.setDescription(requeteGroupeUpdateTO.getDescription());

        return RequeteGroupe2RequeteGroupeReadTO.apply(requeteGroupeRepository.save(found));
    }

    /**
     * Delete a requeteGroupe
     * 
     * @param id
     *            The requeteGroupe id to delete
     * 
     * @throws ResourceNotFoundException
     *             if the requeteGroupe is not found
     */
    public void delete(Long id) {
        // find requeteGroupe
        RequeteGroupe found =findRequeteGroupe(id);
        
        // find all queries associated with this group
        List<Requete> requetes=requeteRepository.findAllByRequeteGroupe(found);
        requetes.forEach(requete->{
            requete.setRequeteGroupe(null);
        });
        
        requeteRepository.save(requetes);
        
        requeteGroupeRepository.delete(id);
    }

    private RequeteGroupe findRequeteGroupe(Long id) {
        // find requeteGroupe
        RequeteGroupe found = requeteGroupeRepository.findOne(id);
        if (found == null) {
            throw new ResourceNotFoundException("The requeteGroupe " + id.toString() + " does not exist");
        }

        return found;
    }

    private Operateur findOperateur(Long id) {
        // find operateur
        Operateur operateur = operateurRepository.findOne(id);
        if (operateur == null) {
            throw new ResourceNotFoundException(
                    String.format("The operateur with the id %s does not exist", id.toString()));
        }

        return operateur;
    }

    /**
     * Update a requeteGroupe
     * 
     * @param requeteGroupeId
     *            The id of the requeteGroupeUpdate to update
     * @param requeteStatusTO
     *            The new status modifications,
     * 
     * @return The requeteGroupeUpdateTO read updated
     * 
     * @throws ResourceNotFoundException
     *             if the requeteGroupe is not found
     * @throws ResourceBadRequestException
     *             if the status does not exist
     */
    public RequeteGroupeReadTO updateStatus(Long requeteGroupeId, IdTO<Long> requeteStatusTO) {
        // find requeteGroupe
        RequeteGroupe found = findRequeteGroupe(requeteGroupeId);

        // find statusRequete
        StatusRequete statusRequete = findStatusRequete(requeteStatusTO.getId());

        // update status
        List<Requete> requetes = requeteRepository.findAllByRequeteGroupe(found);
        if (!requetes.isEmpty()) {
            requetes.forEach(requete -> {
                requete.setStatus(statusRequete);
            });
            requeteRepository.save(requetes);
        }

        // refresh requeteGroupe
        found = requeteGroupeRepository.findOne(found.getId());

        return RequeteGroupe2RequeteGroupeReadTO.apply(found);

    }

    private StatusRequete findStatusRequete(Long id) {
        // find statusRequete
        StatusRequete found = statusRequeteRepository.findOne(id);
        if (found == null) {
            throw new ResourceBadRequestException("The statusRequete " + id.toString() + " does not exist");
        }

        return found;
    }
}
