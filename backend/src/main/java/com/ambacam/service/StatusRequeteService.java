package com.ambacam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambacam.exception.ResourceBadRequestException;
import com.ambacam.exception.ResourceNotFoundException;
import com.ambacam.model.StatusRequete;
import com.ambacam.repository.StatusRequeteRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class StatusRequeteService {
	@Autowired
	private StatusRequeteRepository statusRequeteRepository;

	/**
	 * Create a statusRequete
	 * 
	 * @param statusRequete
	 * 
	 * @return
	 * @throws ResourceBadRequestException if a statusRequete with the nom already exist
	 */
	public StatusRequete create(StatusRequete statusRequete) {

		checkConsistency(statusRequete);

		statusRequete.setId(null);

		return statusRequeteRepository.save(statusRequete);
	}

	/**
	 * Get a statusRequete
	 * 
	 * @param statusRequete
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the statusRequete does not exist
	 */
	public StatusRequete get(Long id) {
		return findStatusRequete(id);
	}

	/**
	 * List all statusRequetes
	 * 
	 * @return
	 */
	public List<StatusRequete> list() {

		return statusRequeteRepository.findAll();
	}

	/**
	 * Update a statusRequete
	 * 
	 * @param id
	 * 
	 * @param update
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the statusRequete is not found
	 * @throws ResourceBadRequestException if a statusRequete with the nom already exist
	 */
	public StatusRequete update(Long id, StatusRequete update) {

		// find statusRequete
		StatusRequete statusRequete = findStatusRequete(id);

		if (!statusRequete.getNom().equals(update.getNom())) {
			checkConsistency(update);
		}

		update.setId(id);
		return statusRequeteRepository.save(update);
	}

	/**
	 * Delete a statusRequete
	 * 
	 * @param statusRequete
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the statusRequete is not found
	 */
	public void delete(Long id) {
		// find statusRequete
		findStatusRequete(id);
		statusRequeteRepository.delete(id);
	}

	private StatusRequete findStatusRequete(Long id) {
		// find statusRequete
		StatusRequete statusRequete = statusRequeteRepository.findOne(id);
		if (statusRequete == null) {
			throw new ResourceNotFoundException("The statusRequete " + id.toString() + " does not exist");
		}

		return statusRequete;
	}

	private void checkConsistency(StatusRequete statusRequete) {

		if (statusRequeteRepository.countByNom(statusRequete.getNom()) > 0) {
			throw new ResourceBadRequestException(
					String.format("A statusRequete with a nom '%s' exist already", statusRequete.getNom()));
		}
	}

}
