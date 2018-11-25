package com.ambacam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambacam.configuration.AppSettings;
import com.ambacam.exception.ResourceBadRequestException;
import com.ambacam.model.Operateur;
import com.ambacam.model.Requerant;
import com.ambacam.model.Requete;
import com.ambacam.model.RequeteGroupe;
import com.ambacam.model.StatusRequete;
import com.ambacam.model.StatusRequeteValues;
import com.ambacam.model.TypeRequete;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.RequerantRepository;
import com.ambacam.repository.RequeteGroupeRepository;
import com.ambacam.repository.RequeteRepository;
import com.ambacam.repository.StatusRequeteRepository;
import com.ambacam.repository.TypeRequeteRepository;
import com.ambacam.search.requetes.RequeteCriteria;
import com.ambacam.search.requetes.RequeteSpecs;
import com.ambacam.transfert.requetes.AssignStatusTO;
import com.ambacam.transfert.requetes.Requete2RequeteReadTO;
import com.ambacam.transfert.requetes.RequeteReadTO;
import com.ambacam.transfert.requetes.RequeteStatusTO;
import com.ambacam.transfert.requetes.RequeteTO;

@Service
@Transactional(rollbackFor = Exception.class)
public class RequeteService {

	@Autowired
	private RequerantRepository requerantRepository;

	@Autowired
	private OperateurRepository operateurRepository;

	@Autowired
	private StatusRequeteRepository statusRequeteRepository;

	@Autowired
	private TypeRequeteRepository typeRequeteRepository;

	@Autowired
	private RequeteRepository requeteRepository;

	@Autowired
	private RequeteGroupeRepository requeteGroupeRepository;

	@Autowired
	private AppSettings appSettings;

	/**
	 * Create a requete
	 * 
	 * @param operateurId
	 * @param requerantId
	 * @param createTO
	 *            the requete to create
	 * 
	 * @return The new requete read created
	 * @throws ResourceNotFoundException
	 *             if the given operateurId does not exist
	 * @throws ResourceNotFoundException
	 *             if the given requerantId does not exist
	 * @throws ResourceBadRequestException
	 *             if the given typeId does not exist
	 * @throws ResourceBadRequestException
	 *             if the requete is not unique
	 */
	public RequeteReadTO create(Long operateurId, Long requerantId, RequeteTO createTO) {
		// find requerant
		Requerant requerant = findRequerant(requerantId);

		// find type
		TypeRequete type = findTypeRequete(createTO.getTypeId());

		// find operateur
		Operateur operateur = findOperateur(operateurId);

		// create requete
		Requete requete = new Requete();
		requete.setId(null);
		requete.setType(type);
		requete.setRequerant(requerant);

		// check uniqueness
		checkUniqueness(requete);

		requete.setOperateur(operateur);
		requete.setStatus(statusRequeteRepository.findByNom(StatusRequeteValues.DRAFT) != null
				? statusRequeteRepository.findByNom(StatusRequeteValues.DRAFT)
				: statusRequeteRepository.save(new StatusRequete().nom(StatusRequeteValues.DRAFT)));

		return Requete2RequeteReadTO.apply(requeteRepository.save(requete));
	}

	/**
	 * List all requetes
	 * 
	 * @return the list of requetes stored
	 */
	public List<RequeteReadTO> listBatch() {
		return requeteRepository.findAll().stream().map(requete -> Requete2RequeteReadTO.apply(requete))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * List all requetes by operateurId
	 * 
	 * @param operateurId
	 * @param limit
	 * @param page
	 * @return the list of requetes stored
	 */
	public List<RequeteReadTO> listByOperateur(Long operateurId, Integer limit, Integer page) {
		Integer searchLimit = limit;
		Integer searchPage = page;

		RequeteCriteria criteria = new RequeteCriteria();
		criteria.setOperateurId(operateurId);
		RequeteSpecs specs = new RequeteSpecs(criteria);

		if (searchLimit == null || searchLimit < appSettings.getSearchDefaultPageSize()) {
			searchLimit = appSettings.getSearchDefaultPageSize();
		}

		if (searchPage == null || searchPage < appSettings.getSearchDefaultPageNumber()) {
			searchPage = appSettings.getSearchDefaultPageNumber();
		}

		Page<Requete> pageRequete = requeteRepository.findAll(specs,
				new PageRequest(searchPage, searchLimit, new Sort(Sort.Direction.ASC, "creeLe")));

		return pageRequete.getContent().stream().map(requete -> Requete2RequeteReadTO.apply(requete))
				.collect(Collectors.toList());

	}

	/**
	 * 
	 * List all requetes by requerantId
	 * 
	 * @param requerantId
	 * @param limit
	 * @param page
	 * @return the list of requetes stored
	 */
	public List<RequeteReadTO> listByRequerant(Long requerantId, Integer limit, Integer page) {
		Integer searchLimit = limit;
		Integer searchPage = page;

		RequeteCriteria criteria = new RequeteCriteria();
		criteria.setRequerantId(requerantId);
		RequeteSpecs specs = new RequeteSpecs(criteria);

		if (searchLimit == null || searchLimit < appSettings.getSearchDefaultPageSize()) {
			searchLimit = appSettings.getSearchDefaultPageSize();
		}

		if (searchPage == null || searchPage < appSettings.getSearchDefaultPageNumber()) {
			searchPage = appSettings.getSearchDefaultPageNumber();
		}

		Page<Requete> pageRequete = requeteRepository.findAll(specs,
				new PageRequest(searchPage, searchLimit, new Sort(Sort.Direction.ASC, "creeLe")));

		return pageRequete.getContent().stream().map(requete -> Requete2RequeteReadTO.apply(requete))
				.collect(Collectors.toList());

	}

	/**
	 * List all requetes by operateurId and requerantId
	 * 
	 * @param operateurId
	 * @param requerantId
	 * @param limit
	 * @param page
	 * @return the list of requetes stored
	 */
	public List<RequeteReadTO> listByOperateurAndRequerant(Long operateurId, Long requerantId, Integer limit,
			Integer page) {
		Integer searchLimit = limit;
		Integer searchPage = page;

		RequeteCriteria criteria = new RequeteCriteria();
		criteria.setOperateurId(operateurId);
		criteria.setRequerantId(requerantId);

		RequeteSpecs specs = new RequeteSpecs(criteria);

		if (searchLimit == null || searchLimit < appSettings.getSearchDefaultPageSize()) {
			searchLimit = appSettings.getSearchDefaultPageSize();
		}

		if (searchPage == null || searchPage < appSettings.getSearchDefaultPageNumber()) {
			searchPage = appSettings.getSearchDefaultPageNumber();
		}

		Page<Requete> pageRequete = requeteRepository.findAll(specs,
				new PageRequest(searchPage, searchLimit, new Sort(Sort.Direction.ASC, "creeLe")));

		return pageRequete.getContent().stream().map(requete -> Requete2RequeteReadTO.apply(requete))
				.collect(Collectors.toList());

	}

	/**
	 * Get a requete
	 * 
	 * @param id
	 * 
	 * @return The requete read found
	 * @throws ResourceNotFoundException
	 *             if the requete does not exist
	 */
	public RequeteReadTO get(Long id) {
		return Requete2RequeteReadTO.apply(findRequete(id));
	}

	/**
	 * Delete a requete
	 * 
	 * @param id
	 *            The id of the requete to delete
	 * @throws ResourceNotFoundException
	 *             if the requete is not found
	 */
	public void delete(Long id) {
		// find requete
		findRequete(id);

		// delete requete
		requeteRepository.delete(id);
	}

	/**
	 * Update a requete
	 * 
	 * @param id
	 *            The id of the requete to update
	 * @param updateTO
	 *            The new requete modifications
	 * 
	 * @return The requete updated
	 * 
	 * @throws ResourceNotFoundException
	 *             if the requete is not found
	 * @throws ResourceBadRequestException
	 *             if the given typeId does not exist
	 * @throws ResourceBadRequestException
	 *             if the requete is not unique
	 */
	public Requete update(Long id, RequeteTO updateTO) {

		// find requete
		Requete found = findRequete(id);

		// find type
		TypeRequete type = findTypeRequete(updateTO.getTypeId());

		// check uniqueness
		if (!found.getType().getId().equals(updateTO.getTypeId())) {
			Requete requete = new Requete().requerant(found.getRequerant()).type(type);
			checkUniqueness(requete);
		}

		// update
		found.setType(type);

		return requeteRepository.save(found);
	}

	/**
	 * Update status of a requete
	 * 
	 * @param id
	 *            The id of the requete to update
	 * @param requeteStatusTO
	 *            The new status modifications
	 * 
	 * @return The requete updated
	 * 
	 * @throws ResourceNotFoundException
	 *             if the requete is not found
	 * @throws ResourceBadRequestException
	 *             if the given statusId does not exist
	 */
	public Requete updateStatus(Long id, RequeteStatusTO requeteStatusTO) {

		// find requete
		Requete found = findRequete(id);

		// find status
		StatusRequete status = findStatusRequete(requeteStatusTO.getStatusId());

		// update
		found.status(status);

		return requeteRepository.save(found);
	}

	/**
	 * Update status of some Requetes in a RequeteGroupe
	 * 
	 * @param requeteGroupeId
	 * @param assignStatusTO
	 * 
	 * @throws ResourceNotFoundException
	 *             if the requeteGroupe is not found
	 * @throws ResourceBadRequestException
	 *             if the given statusId does not exist
	 * @throws ResourceBadRequestException
	 *             if the requete groupe does not contains a requete
	 * @throws ResourceBadRequestException
	 *             if a requete to update does not exist
	 * @throws ResourceBadRequestException
	 *             if a requete already has the given status
	 */
	public void assignStatus(Long requeteGroupeId, AssignStatusTO assignStatusTO) {
		// find requete groupe
		RequeteGroupe requeteGroupe = findRequeteGroupe(requeteGroupeId);

		// find status
		StatusRequete status = findStatusRequete(assignStatusTO.getStatusId());

		// find requetes
		List<Requete> requetes = requeteRepository.findAllByRequeteGroupe(requeteGroupe);

		assignStatusTO.getRequeteIds().forEach(requeteId -> {
			Requete requete = requeteRepository.findOne(requeteId);
			if (requete == null) {
				throw new ResourceBadRequestException("The requete " + requeteId + " does not exist");
			}
			if (!requetes.contains(requete)) {
				throw new ResourceBadRequestException(
						"The requete groupe " + requeteGroupe.getId() + " does not contains the requete " + requeteId);
			}
			if (requete.getStatus().getId() == status.getId()) {
				throw new ResourceBadRequestException(
						"The requete " + requeteId + " already has the status " + status.getId());
			}

			// update status
			requete.setStatus(status);
			requeteRepository.save(requete);
		});
	}

	/**
	 * Get all requetes of a single requete groupe
	 * 
	 * @param requeteGroupeId
	 * @return
	 * @throws ResourceNotFoundException
	 *             if the requeteGroupe is not found
	 */
	public List<RequeteReadTO> listRequetes(Long requeteGroupeId) {

		RequeteGroupe requeteGroupe = findRequeteGroupe(requeteGroupeId);
		return requeteRepository.findAllByRequeteGroupe(requeteGroupe).stream()
				.map(requete -> Requete2RequeteReadTO.apply(requete)).collect(Collectors.toList());
	}

	private Requete findRequete(Long id) {
		Requete requete = requeteRepository.findOne(id);
		if (requete == null) {
			throw new ResourceNotFoundException(
					String.format("The requete with the id %s does not exist", id.toString()));
		}
		return requete;
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

	private Requerant findRequerant(Long id) {
		// find requerant
		Requerant found = requerantRepository.findOne(id);
		if (found == null) {
			throw new ResourceNotFoundException(
					String.format("The requerant with the id %s does not exist", id.toString()));
		}
		return found;
	}

	private StatusRequete findStatusRequete(Long id) {
		// find statusRequete
		StatusRequete found = statusRequeteRepository.findOne(id);
		if (found == null) {
			throw new ResourceBadRequestException("The statusRequete " + id.toString() + " does not exist");
		}

		return found;
	}

	private TypeRequete findTypeRequete(Long id) {
		// find typeRequete
		TypeRequete found = typeRequeteRepository.findOne(id);
		if (found == null) {
			throw new ResourceBadRequestException("The typeRequete " + id.toString() + " does not exist");
		}

		return found;
	}

	private RequeteGroupe findRequeteGroupe(Long id) {
		// find requeteGroupe
		RequeteGroupe found = requeteGroupeRepository.findOne(id);
		if (found == null) {
			throw new ResourceNotFoundException("The requeteGroupe " + id.toString() + " does not exist");
		}

		return found;
	}

	private void checkUniqueness(Requete requete) {
		if (requeteRepository.countByRequerantAndType(requete.getRequerant(), requete.getType()) > 0) {
			throw new ResourceBadRequestException(
					"A requete for the requerant " + requete.getRequerant().getId().toString() + " with the type "
							+ requete.getType().getId().toString() + " already exist");

		}
	}

	public AppSettings getAppSettings() {
		return appSettings;
	}

	public void setAppSettings(AppSettings appSettings) {
		this.appSettings = appSettings;
	}

}
