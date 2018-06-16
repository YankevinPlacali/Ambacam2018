package com.ambacam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambacam.exception.ResourceBadRequestException;
import com.ambacam.exception.ResourceNotFoundException;
import com.ambacam.model.Autorite;
import com.ambacam.repository.AutoriteRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class AutoriteService {
	@Autowired
	private AutoriteRepository autoriteRepository;

	/**
	 * Create an autorite
	 * 
	 * @param autorite The new autorite to create
	 * 
	 * @return The new autorite created
	 * @throws ResourceBadRequestException if an autorite with the name already exist
	 */
	public Autorite create(Autorite autorite) {

		autorite.setId(null);

		return autoriteRepository.save(autorite);
	}

	/**
	 * Get an autorite
	 * 
	 * @param id The id of the Autorite you want to get
	 * 
	 * @return The autorite found
	 * 
	 * @throws ResourceNotFoundException if the autorite does not exist
	 */
	public Autorite get(Long id) {
		return findAutorite(id);
	}

	/**
	 * List all autorites
	 * 
	 * @return The list of autorite stored
	 */
	public List<Autorite> list() {

		return autoriteRepository.findAll();
	}

	/**
	 * Update an autorite
	 * 
	 * @param id The id of the autorite to update
	 * 
	 * @param autorite The new autorite modifications
	 * 
	 * @return The autorite updated
	 * 
	 * @throws ResourceNotFoundException if the autorite is not found
	 * @throws ResourceBadRequestException if an autorite with the name already exist
	 */
	public Autorite update(Long id, Autorite autorite) {

		// find autorite
		findAutorite(id);

		autorite.setId(id);
		return autoriteRepository.save(autorite);
	}

	/**
	 * Delete an autorite
	 * 
	 * @param id The id of the autorite to delete
	 * 
	 * @throws ResourceNotFoundException if the autorite is not found
	 */
	public void delete(Long id) {
		// find autorite
		findAutorite(id);
		autoriteRepository.delete(id);
	}

	private Autorite findAutorite(Long id) {
		// find autorite
		Autorite autorite = autoriteRepository.findOne(id);
		if (autorite == null) {
			throw new ResourceNotFoundException("The autorite " + id.toString() + " does not exist");
		}

		return autorite;
	}

}
