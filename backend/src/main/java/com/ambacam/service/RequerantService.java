package com.ambacam.service;

import java.util.List;
import java.util.Random;
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
import com.ambacam.model.Requerant;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PaysRepository;
import com.ambacam.repository.RequerantRepository;
import com.ambacam.search.requerants.RequerantCriteria;
import com.ambacam.search.requerants.RequerantSpecs;
import com.ambacam.transfert.SearchResultTO;
import com.ambacam.transfert.requerants.Requerant2RequerantReadTO;
import com.ambacam.transfert.requerants.RequerantCreateTO;
import com.ambacam.transfert.requerants.RequerantReadTO;
import com.ambacam.transfert.requerants.RequerantUpdateTO;

@Service
@Transactional(rollbackFor = Exception.class)
public class RequerantService {

	@Autowired
	private RequerantRepository requerantRepository;

	@Autowired
	private OperateurRepository operateurRepository;

	@Autowired
	private PaysRepository paysRepository;

	@Autowired
	private AppSettings appSettings;

	/**
	 * Create a requerant
	 * 
	 * @param requerantCreateTO
	 *            The new requerant to create
	 * 
	 * @return The new requerant read created
	 * @throws ResourceBadRequestException
	 *             if the given paysId does not exist
	 * @throws ResourceBadRequestException
	 *             if the given creatorId does not exist
	 */
	public RequerantReadTO create(RequerantCreateTO requerantCreateTO) {

		// find pays
		Pays pays = findPays(requerantCreateTO.getPaysId());

		// find creator operateur
		Operateur creator = findOperateurCreator(requerantCreateTO.getCreatorId());

		// generate identifier
		String identifier = generateString(appSettings.getRequerantIdentifierLength());

		// create the new requerant
		Requerant requerant = new Requerant().id(null).nom(requerantCreateTO.getNom())
				.prenom(requerantCreateTO.getPrenom()).dateNaissance(requerantCreateTO.getDateNaissance())
				.creePar(creator).sexe(requerantCreateTO.getSexe()).profession(requerantCreateTO.getProfession())
				.lieuNaissance(requerantCreateTO.getLieuNaissance()).nationalite(pays).identifier(identifier);

		// save requerant
		return Requerant2RequerantReadTO.apply(requerantRepository.save(requerant));
	}

	/**
	 * Get a requerant
	 * 
	 * @param id
	 *            The id of the Requerant you want to get
	 * 
	 * @return The requerant read found
	 * 
	 * @throws ResourceNotFoundException
	 *             if the requerant does not exist
	 */
	public RequerantReadTO get(Long id) {
		return Requerant2RequerantReadTO.apply(findRequerant(id));
	}

	/**
	 * List all requerants
	 * 
	 * @return The list of requerant stored
	 */
	public List<RequerantReadTO> list() {

		return requerantRepository.findAll().stream().map(requerant -> Requerant2RequerantReadTO.apply(requerant))
				.collect(Collectors.toList());
	}

	/**
	 * Find requerants by parameters
	 * 
	 * @param limit
	 * @param page
	 * @param criteria
	 * @return
	 */
	public SearchResultTO<RequerantReadTO> search(Integer limit, Integer page, RequerantCriteria criteria) {

		Integer searchLimit = limit;
		Integer searchPage = page;

		// build specs
		RequerantSpecs specs = new RequerantSpecs(criteria);

		if (searchLimit == null || searchLimit < appSettings.getSearchDefaultPageSize()) {
			searchLimit = appSettings.getSearchDefaultPageSize();
		}

		if (searchPage == null || searchPage < appSettings.getSearchDefaultPageNumber()) {
			searchPage = appSettings.getSearchDefaultPageNumber();
		}

		Page<Requerant> pageRequerant = requerantRepository.findAll(specs,
				new PageRequest(searchPage, searchLimit, new Sort(Sort.Direction.ASC, "nom")));

		SearchResultTO<RequerantReadTO> requerantSearchTO = new SearchResultTO<RequerantReadTO>();
		requerantSearchTO.setContent(pageRequerant.getContent().stream()
				.map(requerant -> Requerant2RequerantReadTO.apply(requerant)).collect(Collectors.toList()));
		requerantSearchTO.setPage(pageRequerant.getNumber());
		requerantSearchTO.setTotalPages(pageRequerant.getTotalPages());
		return requerantSearchTO;

	}

	/**
	 * Update a requerant
	 * 
	 * @param id
	 *            The id of the requerant to update
	 * 
	 * @param requerantUpdateTO
	 *            The new requerant modifications
	 * 
	 * @return The requerant updated
	 * 
	 * @throws ResourceNotFoundException
	 *             if the requerant is not found
	 * @throws ResourceBadRequestException
	 *             if the given paysId does not exist
	 */
	public Requerant update(Long id, RequerantUpdateTO requerantUpdateTO) {

		// find existing requerant
		Requerant found = findRequerant(id);

		// find pays
		Pays pays = findPays(requerantUpdateTO.getPaysId());

		// set properties
		found.setNom(requerantUpdateTO.getNom());
		found.setPrenom(requerantUpdateTO.getPrenom());
		found.setDateNaissance(requerantUpdateTO.getDateNaissance());
		found.setSexe(requerantUpdateTO.getSexe());
		found.setProfession(requerantUpdateTO.getProfession());
		found.setLieuNaissance(requerantUpdateTO.getLieuNaissance());
		found.setNationalite(pays);

		// save update
		return requerantRepository.save(found);
	}

	/**
	 * Delete a requerant
	 * 
	 * @param id
	 *            The id of the requerant to delete
	 * 
	 * @throws ResourceNotFoundException
	 *             if the requerant is not found
	 */
	public void delete(Long id) {
		// find requerant
		findRequerant(id);

		// delete
		requerantRepository.delete(id);
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

	private String generateString(int lengthChar) {
		int i, randomNum;
		Random r = new Random();
		String newId = "";
		int ascii_min_char_maj = 65;
		int ascii_max_char_maj = 90;
		int ascii_min_char_min = 97;
		int ascii_max_char_min = 122;
		int min_int = 48;
		int max_int = 57;
		int rd;
		for (i = 0; i < lengthChar; i++) {
			rd = r.nextInt((2 - 0) + 1) + 0;
			randomNum = 0;
			if (rd == 0)
				randomNum = r.nextInt((ascii_max_char_maj - ascii_min_char_maj) + 1) + ascii_min_char_maj;
			if (rd == 1)
				randomNum = r.nextInt((ascii_max_char_min - ascii_min_char_min) + 1) + ascii_min_char_min;
			if (rd == 2)
				randomNum = r.nextInt((max_int - min_int) + 1) + min_int;
			newId += (char) randomNum;
		}

		return newId;
	}
}
