package com.ambacam.service;

import com.ambacam.exception.ResourceBadRequestException;
import com.ambacam.exception.ResourceNotFoundException;
import com.ambacam.model.TypeRequete;
import com.ambacam.repository.TypeRequeteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TypeRequeteService {


	@Autowired
	private TypeRequeteRepository typeRequeteRepository;

	/**
	 * Create a typeRequete
	 * 
	 * @param typeRequete which need to be created
	 * 
	 * @return the specific typeRequete which has been created
	 * @throws ResourceBadRequestException if a typeRequete with the name already exist
	 */
	public TypeRequete create(TypeRequete typeRequete) {

		checkConsistency(typeRequete);

		typeRequete.setId(null);

		return typeRequeteRepository.save(typeRequete);
	}

	/**
	 * Get a typeRequete
	 * 
	 * @param id the id for the typeRequete to seek for
	 * 
	 * @return the specific typeRequete corresponding to the giving id
	 * 
	 * @throws ResourceNotFoundException if the typeRequete does not exist
	 */
	public TypeRequete get(Long id) {
		return findTypeRequete(id);
	}

	/**
	 * List all typeRequetes
	 * 
	 * @return the list of all available typeRequetes
	 */
	public List<TypeRequete> list() {

		return typeRequeteRepository.findAll();
	}

	/**
	 * Update a typeRequete
	 * 
	 * @param id  the id for the typeRequete to be updated
	 * 
	 * @param update the typeRequete which need to be updated
	 * 
	 * @return the specific typeRequete which was updated
	 * 
	 * @throws ResourceNotFoundException if the typeRequete is not found
	 * @throws ResourceBadRequestException if a typeRequete with the name already exist
	 */
	public TypeRequete update(Long id, TypeRequete update) {

		TypeRequete found = findTypeRequete(id);

		if (!found.getNom().equals(update.getNom())) {
			checkConsistency(update);
		}

		update.setId(id);
		return typeRequeteRepository.save(update);
	}

	/**
	 * Delete a typeRequete
	 * 
	 * @param id he id for the typeRequete to be deleted
	 * 
	 * @throws ResourceNotFoundException if the typeRequete is not found
	 */
	public void delete(Long id) {
		// find typeRequete
		findTypeRequete(id);
		typeRequeteRepository.delete(id);
	}

	private TypeRequete findTypeRequete(Long id) {
		// find typeRequete
		TypeRequete found = typeRequeteRepository.findOne(id);
		if (found == null) {
			throw new ResourceNotFoundException("The typeRequete " + id.toString() + " does not exist");
		}

		return found;
	}

	private void checkConsistency(TypeRequete typeRequete) {

		if (typeRequeteRepository.countByNom(typeRequete.getNom()) > 0) {
			throw new ResourceBadRequestException(
					String.format("A typeRequete with a name '%s' exist already", typeRequete.getNom()));
		}
	}

}
