package com.ambacam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambacam.exception.ResourceBadRequestException;
import com.ambacam.model.Autorite;
import com.ambacam.model.Passport;
import com.ambacam.model.Requete;
import com.ambacam.repository.AutoriteRepository;
import com.ambacam.repository.PassportRepository;
import com.ambacam.repository.RequeteRepository;
import com.ambacam.transfert.passports.Passport2PassportReadTO;
import com.ambacam.transfert.passports.PassportCreateTO;
import com.ambacam.transfert.passports.PassportReadTO;

@Service
@Transactional(rollbackFor = Exception.class)
public class PassportService {
	@Autowired
	private RequeteRepository requeteRepository;

	@Autowired
	private AutoriteRepository autoriteRepository;

	@Autowired
	private PassportRepository passportRepository;

	/**
	 * Create a passport
	 * 
	 * @param requeteId
	 * @param passportCreateTO
	 * 
	 * @return the new passport created
	 * 
	 * @throws ResourceNotFoundException
	 *             if the requete is not found
	 * @throws ResourceBadRequestException
	 *             if the autorite does not exist
	 * @throws ResourceBadRequestException
	 *             if a passport with the same number already exist
	 * @throws ResourceBadRequestException
	 *             if the expiration date is before the date of deliver
	 */
	public Passport create(Long requeteId, PassportCreateTO passportCreateTO) {

		// check the expiration date and the deliver date
		if (passportCreateTO.getDateExpiration().before(passportCreateTO.getDateDelivrance())) {
			throw new ResourceBadRequestException("The expiration date is before the date of deliver");
		}

		// find requete
		Requete requete = findRequete(requeteId);

		// find autorite
		Autorite delivrePar = findAutorite(passportCreateTO.getAutoriteId());

		Passport passport = new Passport();
		passport.setId(null);
		passport.setNumero(passportCreateTO.getNumero());

		// check passport number uniqueness
		checkNumberUniqueness(passport);

		passport.setDateDelivrance(passportCreateTO.getDateDelivrance());
		passport.setDateExpiration(passportCreateTO.getDateExpiration());
		passport.setLieuDelivrance(passportCreateTO.getLieuDelivrance());
		passport.setDelivrePar(delivrePar);
		passport.setRequete(requete);

		return passportRepository.save(passport);
	}

	/**
	 * Get a passport
	 * 
	 * @param id
	 * 
	 * @return the passport found
	 * @throws ResourceNotFoundException
	 *             if the passport is not found
	 */
	public PassportReadTO get(Long id) {
		return Passport2PassportReadTO.apply(findPassport(id));
	}

	/**
	 * List all passports by requeteId
	 * 
	 * @param requeteId
	 * 
	 * @return The list of passport stored
	 */
	public List<PassportReadTO> listByRequete(Long requeteId) {
		return passportRepository.findAllByRequeteId(requeteId).stream()
				.map(passport -> Passport2PassportReadTO.apply(passport)).collect(Collectors.toList());
	}

	/**
	 * Delete a passport
	 * 
	 * @param id
	 * 
	 * @throws ResourceNotFoundException
	 *             if the passport is not found
	 */
	public void delete(Long id) {
		// find passport
		findPassport(id);

		// delete passport
		passportRepository.delete(id);
	}

	/**
	 * Update a passport
	 * 
	 * @param passportId
	 *            The id of the passport to update
	 * @param passportUpdateTO
	 *            The new passport modifications
	 * 
	 * @return passport updated
	 * 
	 * @throws ResourceNotFoundException
	 *             if the passport is not found
	 * @throws ResourceBadRequestException
	 *             if the autorite does not exist
	 * @throws ResourceBadRequestException
	 *             if a passport with the same number already exist
	 * @throws ResourceBadRequestException
	 *             if the expiration date is before the date of deliver
	 */
	public Passport update(Long passportId, PassportCreateTO passportUpdateTO) {

		// check the expiration date and the deliver date
		if (passportUpdateTO.getDateExpiration().before(passportUpdateTO.getDateDelivrance())) {
			throw new ResourceBadRequestException("The expiration date is before the date of deliver");
		}

		// find autorite
		Autorite delivrePar = findAutorite(passportUpdateTO.getAutoriteId());

		Passport found = findPassport(passportId);

		if (!found.getNumero().equals(passportUpdateTO.getNumero())) {
			Passport passport = new Passport().numero(passportUpdateTO.getNumero());
			// check passport number uniqueness
			checkNumberUniqueness(passport);
		}

		found.setNumero(passportUpdateTO.getNumero());
		found.setDateDelivrance(passportUpdateTO.getDateDelivrance());
		found.setLieuDelivrance(passportUpdateTO.getLieuDelivrance());
		found.setDateExpiration(passportUpdateTO.getDateExpiration());
		found.setDelivrePar(delivrePar);
		return passportRepository.save(found);
	}

	private Passport findPassport(Long id) {
		Passport passport = passportRepository.findOne(id);
		if (passport == null) {
			throw new ResourceNotFoundException(
					String.format("The passport with the id %s does not exist", id.toString()));
		}
		return passport;
	}

	private Requete findRequete(Long id) {
		Requete requete = requeteRepository.findOne(id);
		if (requete == null) {
			throw new ResourceNotFoundException(
					String.format("The requete with the id %s does not exist", id.toString()));
		}
		return requete;
	}

	private Autorite findAutorite(Long id) {
		// find autorite
		Autorite found = autoriteRepository.findOne(id);
		if (found == null) {
			throw new ResourceBadRequestException("The autorite " + id.toString() + " does not exist");
		}
		return found;
	}

	private void checkNumberUniqueness(Passport passport) {
		if (passportRepository.countByNumero(passport.getNumero()) > 0) {
			throw new ResourceBadRequestException(
					String.format("A passport with the number %s already exist", passport.getNumero()));
		}
	}
}
