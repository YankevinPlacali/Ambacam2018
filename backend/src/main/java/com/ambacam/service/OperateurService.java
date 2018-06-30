package com.ambacam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambacam.configuration.AppSettings;
import com.ambacam.exception.ResourceBadRequestException;
import com.ambacam.exception.ResourceNotFoundException;
import com.ambacam.model.Operateur;
import com.ambacam.model.Pays;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PaysRepository;
import com.ambacam.search.operateurs.OperateurCriteria;
import com.ambacam.search.operateurs.OperateurSpecs;
import com.ambacam.transfert.operateurs.Operateur2OperateurReadTO;
import com.ambacam.transfert.operateurs.OperateurCreateTO;
import com.ambacam.transfert.operateurs.OperateurCreateTO2Operateur;
import com.ambacam.transfert.operateurs.OperateurReadTO;
import com.ambacam.transfert.operateurs.OperateurSearchTO;
import com.ambacam.transfert.operateurs.OperateurUpdateTO;

@Service
@Transactional(rollbackFor = Exception.class)
public class OperateurService {
	@Autowired
	private OperateurRepository operateurRepository;

	@Autowired
	private PaysRepository paysRepository;

	@Autowired
	private AppSettings appSettings;

	/**
	 * Create an operateur
	 * 
	 * @param operateurCreateTO The new operateur to create
	 * 
	 * @return The new operateur read created
	 * @throws ResourceBadRequestException if the given paysId does not exist
	 * @throws ResourceBadRequestException if the given creatorId does not exist
	 */
	public OperateurReadTO create(OperateurCreateTO operateurCreateTO) {

		// find pays
		Pays pays = findPays(operateurCreateTO.getPaysId());

		// find creator operateur
		Operateur creator = findOperateurCreator(operateurCreateTO.getCreatorId());

		// create the new operateur
		Operateur operateur = OperateurCreateTO2Operateur.apply(operateurCreateTO);

		// set properties
		operateur.setCreePar(creator);
		operateur.setNationalite(pays);

		// save operateur
		return Operateur2OperateurReadTO.apply(operateurRepository.save(operateur));
	}

	/**
	 * Get an operateur
	 * 
	 * @param id The id of the Operateur you want to get
	 * 
	 * @return The operateur read found
	 * 
	 * @throws ResourceNotFoundException if the operateur does not exist
	 */
	public OperateurReadTO get(Long id) {
		return Operateur2OperateurReadTO.apply(findOperateur(id));
	}

	/**
	 * List all operateurs
	 * 
	 * @return The list of operateur stored
	 */
	public List<OperateurReadTO> list() {

		return operateurRepository.findAll().stream().map(operateur -> Operateur2OperateurReadTO.apply(operateur))
				.collect(Collectors.toList());
	}

	/**
	 * Find operateurs by parameters
	 * 
	 * @param limit
	 * @param page
	 * @param criteria
	 * @return
	 */
	public OperateurSearchTO search(Integer limit, Integer page, OperateurCriteria criteria) {

		Integer searchLimit = limit;
		Integer searchPage = page;

		// build specs
		OperateurSpecs specs = new OperateurSpecs(criteria);

		if (searchLimit == null || searchLimit < appSettings.getSearchDefaultPageSize()) {
			searchLimit = appSettings.getSearchDefaultPageSize();
		}

		if (searchPage == null || searchPage < appSettings.getSearchDefaultPageNumber()) {
			searchPage = appSettings.getSearchDefaultPageNumber();
		}

		Page<Operateur> pageOperateur = operateurRepository.findAll(specs,
				new PageRequest(searchPage, searchLimit, new Sort(Sort.Direction.ASC, "nom")));

		OperateurSearchTO operateurSearchTO = new OperateurSearchTO();
		operateurSearchTO.setOperateurs(pageOperateur.getContent().stream()
				.map(operateur -> Operateur2OperateurReadTO.apply(operateur)).collect(Collectors.toList()));
		operateurSearchTO.setPage(pageOperateur.getNumber());
		operateurSearchTO.setTotalPages(pageOperateur.getTotalPages());
		return operateurSearchTO;

	}

	/**
	 * Update an operateur
	 * 
	 * @param id The id of the operateur to update
	 * 
	 * @param operateurUpdateTO The new operateur modifications
	 * 
	 * @return The operateur updated
	 * 
	 * @throws ResourceNotFoundException if the operateur is not found
	 * @throws ResourceBadRequestException if the given paysId does not exist
	 */
	public Operateur update(Long id, OperateurUpdateTO operateurUpdateTO) {

		// find existing operateur
		Operateur operateur = findOperateur(id);

		// find pays
		Pays pays = findPays(operateurUpdateTO.getPaysId());

		// set properties
		operateur.setNom(operateurUpdateTO.getNom());
		operateur.setPrenom(operateurUpdateTO.getPrenom());
		operateur.setSexe(operateurUpdateTO.getSexe());
		operateur.setNationalite(pays);

		// save update
		return operateurRepository.save(operateur);
	}

	/**
	 * Delete an operateur
	 * 
	 * @param id The id of the operateur to delete
	 * 
	 * @throws ResourceNotFoundException if the operateur is not found
	 */
	public void delete(Long id) {
		// find operateur
		findOperateur(id);

		// set unlink all created operateur
		List<Operateur> createdOperateurs = operateurRepository.findByCreeParId(id);
		createdOperateurs.forEach(operateur -> {
			operateur.setCreePar(null);
			operateurRepository.save(operateur);
		});

		operateurRepository.delete(id);
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

	private Operateur findOperateurCreator(Long id) {
		// find operateur
		Operateur operateur = operateurRepository.findOne(id);
		if (operateur == null) {
			throw new ResourceBadRequestException(
					String.format("The operateur with the id %s does not exist", id.toString()));
		}

		return operateur;
	}

	private Pays findPays(Long id) {

		// find pays
		Pays pays = paysRepository.findOne(id);

		if (pays == null) {
			throw new ResourceBadRequestException(
					String.format("The pays with the id %s does not exist", id.toString()));
		}
		return pays;
	}

	public AppSettings getAppSettings() {
		return appSettings;
	}

	public void setAppSettings(AppSettings appSettings) {
		this.appSettings = appSettings;
	}

}