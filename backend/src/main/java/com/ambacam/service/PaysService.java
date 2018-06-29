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
	private PaysRepository paysRepository;

	/**
	 * Create a Pays
	 * 
	 * @param pays The new pays to create
	 * 
	 * @return The new pays created
	 * @throws ResourceBadRequestException if a pays with the name already exist
	 */
	public Pays create(Pays pays) {

		checkConsistency(pays);

		pays.setId(null);

		return paysRepository.save(pays);
	}

	/**
	 * Get a pays
	 * 
	 * @param id The id of the pays you want to get
	 * 
	 * @return The pays found
	 * 
	 * @throws ResourceNotFoundException if the pays does not exist
	 */
	public Pays get(Long id) {
		return findPays(id);
	}

	/**
	 * List all pays
	 * 
	 * @return The list of pays stored
	 */
	public List<Pays> list() {

		return paysRepository.findAll();
	}

	/**
	 * Update a pays
	 * 
	 * @param id The id of the pays to update
	 * 
	 * @param update The new pays modifications
	 * 
	 * @return The pays updated
	 * 
	 * @throws ResourceNotFoundException if the pays is not found
	 * @throws ResourceBadRequestException if a pays with the name already exist
	 */
	public Pays update(Long id, Pays update) {

		// find pays
		Pays found = findPays(id);

		if (!found.getNom().equals(update.getNom())) {
			checkConsistency(update);
		}

		update.setId(id);
		return paysRepository.save(update);
	}

	/**
	 * Delete a pays
	 * 
	 * @param id The id of the pays to delete
	 * 
	 * @throws ResourceNotFoundException if the pays is not found
	 */
	public void delete(Long id) {
		// find pays
		findPays(id);
		paysRepository.delete(id);
	}

	private Pays findPays(Long id) {
		// find pays
		Pays found = paysRepository.findOne(id);
		if (found == null) {
			throw new ResourceNotFoundException("The pays " + id.toString() + " does not exist");
		}

		return found;
	}

	private void checkConsistency(Pays pays) {

		if (paysRepository.countByNom(pays.getNom()) > 0) {
			throw new ResourceBadRequestException(
					String.format("A Pays with a name '%s' exist already", pays.getNom()));
		}
	}

}
