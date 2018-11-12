package com.ambacam.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambacam.configuration.AppSettings;
import com.ambacam.model.Action;
import com.ambacam.model.Log;
import com.ambacam.model.LogActeur;
import com.ambacam.model.MotifSuppression;
import com.ambacam.model.Operateur;
import com.ambacam.model.Passport;
import com.ambacam.model.Requete;
import com.ambacam.model.RequeteGroupe;
import com.ambacam.repository.ActionRepository;
import com.ambacam.repository.LogRepository;
import com.ambacam.repository.MotifSuppressionRepository;
import com.ambacam.search.logs.LogCriteria;
import com.ambacam.search.logs.LogSpecs;
import com.ambacam.transfert.SearchResultTO;

@Service
@Transactional(rollbackFor = Exception.class)
public class LogService {
	@Autowired
	private LogRepository logRepository;
	@Autowired
	private MotifSuppressionRepository motifSuppressionRepository;
	@Autowired
	private ActionRepository actionRepository;
	@Autowired
	private AppSettings appSettings;

	/**
	 * Create a log
	 * 
	 * @param log
	 * 
	 * @return
	 */
	public Log create(Log log) {

		log.setId(null);

		return logRepository.save(log);
	}

	/**
	 * List all logs
	 * 
	 * @param limit
	 * @param page
	 * @param criteria
	 * @return
	 */
	public SearchResultTO<Log> list(Integer limit, Integer page, LogCriteria criteria) {

		Integer searchLimit = limit;
		Integer searchPage = page;

		// build specs
		LogSpecs specs = new LogSpecs(criteria);

		if (searchLimit == null || searchLimit < appSettings.getSearchDefaultPageSize()) {
			searchLimit = appSettings.getSearchDefaultPageSize();
		}

		if (searchPage == null || searchPage < appSettings.getSearchDefaultPageNumber()) {
			searchPage = appSettings.getSearchDefaultPageNumber();
		}

		Page<Log> pageLog = logRepository.findAll(specs,
				new PageRequest(searchPage, searchLimit, new Sort(Sort.Direction.ASC, "date")));

		SearchResultTO<Log> logSearchTO = new SearchResultTO<Log>();
		logSearchTO.setContent(pageLog.getContent());
		logSearchTO.setPage(pageLog.getNumber());
		logSearchTO.setTotalPages(pageLog.getTotalPages());
		return logSearchTO;

	}

	public AppSettings getAppSettings() {
		return appSettings;
	}

	public void setAppSettings(AppSettings appSettings) {
		this.appSettings = appSettings;
	}

	public void create(Operateur operateur, Object object, String actionName, String motifName) {
		// build acteur actif
		Map<String, String> propertiesActeurActif = buildPropertiesActeurActif(operateur);
		LogActeur acteurActif = buildLogActeur(operateur, propertiesActeurActif);

		// build log acteur passif
		Map<String, String> propertiesActeurPassif = buildPropertiesActeurPassif(object);
		LogActeur acteurPassif = buildLogActeur(object, propertiesActeurPassif);

		// build action
		Action action = new Action(ActionName.DELETE);

		// create log
		createLog(acteurActif, acteurPassif, action, motifName);
	}

	private void createLog(LogActeur acteurActif, LogActeur acteurPassif, Action action, String motifName) {
		MotifSuppression motif = new MotifSuppression();
		// find action
		if (actionRepository.findByNom(action.getNom()) == null) {
			actionRepository.save(action);
		}

		// find motif suppression
		if (motifName == null || motifName.isEmpty()) {
			motif.setNom(MotifSuppressionService.DEFAULT_MOTIF);
		} else {
			motif.setNom(motifName);
		}
		if (motifSuppressionRepository.findByNom(motif.getNom()) == null) {
			motifSuppressionRepository.save(motif);
		}

		// create log
		Log log = new Log(acteurActif, acteurPassif, action, motif);

		logRepository.save(log);
	}

	private static LogActeur buildLogActeur(Object acteur, Map<String, String> properties) {

		LogActeur item = new LogActeur();
		item.setActeur(acteur);
		item.setProperties(properties);

		return item;
	}

	private Map<String, String> buildPropertiesActeurPassif(Object object) {

		Map<String, String> properties = new HashMap<>();
		if (object instanceof RequeteGroupe) {
			RequeteGroupe requeteGroupe = (RequeteGroupe) object;
			properties.put("Id", requeteGroupe.getId().toString());
			properties.put("Name", requeteGroupe.getNom());
			properties.put("Description", requeteGroupe.getDescription());
			properties.put("Status", requeteGroupe.getStatus());
			properties.put("Creation date", requeteGroupe.getCreeLe().toString());
			properties.put("Create by", requeteGroupe.getCreePar().getId().toString());
		}

		if (object instanceof Passport) {
			Passport passport = (Passport) object;
			properties.put("Id", passport.getId().toString());
			properties.put("Nummer", passport.getNumero());
			properties.put("Expiration date", passport.getDateExpiration().toString());
			properties.put("Autority id", passport.getDelivrePar().getId().toString());
		}

		if (object instanceof Requete) {
			Requete requete = (Requete) object;
			properties.put("Id", requete.getId().toString());
			properties.put("Status", requete.getStatus().getNom());
			properties.put("Creation date", requete.getCreeLe().toString());
			properties.put("Requete groupe id", requete.getRequeteGroupe().getId().toString());
			properties.put("Requerant id", requete.getRequerant().getId().toString());
			properties.put("Operateur id", requete.getOperateur().getId().toString());
		}

		return properties;
	}

	private Map<String, String> buildPropertiesActeurActif(Operateur operateur) {
		Map<String, String> properties = new HashMap<>();
		properties.put("Id", operateur.getId().toString());
		properties.put("Name", operateur.getNom());
		properties.put("First name", operateur.getPrenom());
		properties.put("Sexe", operateur.getSexe());
		properties.put("Nationality", operateur.getNationalite().getNom());
		properties.put("Creation date", operateur.getCreeLe().toString());
		return properties;
	}
}
