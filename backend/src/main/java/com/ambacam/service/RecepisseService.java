package com.ambacam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambacam.exception.ResourceBadRequestException;
import com.ambacam.exception.ResourceNotFoundException;
import com.ambacam.model.Recepisse;
import com.ambacam.model.Requete;
import com.ambacam.repository.RecepisseRepository;
import com.ambacam.repository.RequeteRepository;
import com.ambacam.transfert.recepisses.Recepisse2RecepisseReadTO;
import com.ambacam.transfert.recepisses.RecepisseCreateTO;
import com.ambacam.transfert.recepisses.RecepisseReadTO;
import com.ambacam.transfert.recepisses.RecepisseUpdateTO;

@Service
@Transactional(rollbackFor = Exception.class)
public class RecepisseService {

	@Autowired
	private RecepisseRepository recepisseRepository;

	@Autowired
	private RequeteRepository requeteRepository;

	/**
	 * Create a recepisse
	 *
	 * @param requeteId
	 * @param recepisseCreateTO The new recepisse to create
	 * @return The new recepisse read created
	 * @throws ResourceBadRequestException if the given requeteId does not exist
	 */
	public RecepisseReadTO create(Long requeteId, RecepisseCreateTO recepisseCreateTO) {

		// find requete
		Requete requete = findRequete(requeteId);

		// create the new recepisse
		Recepisse recepisse = new Recepisse().numero(recepisseCreateTO.getNumero()).requete(requete);

		// save recepisse
		return Recepisse2RecepisseReadTO.apply(recepisseRepository.save(recepisse));
	}

	/**
	 * Get a recepisse
	 *
	 * @param id The id of the recepisse you want to get
	 * @return The recepisse read found
	 * @throws ResourceNotFoundException if the recepisse does not exist
	 */
	public RecepisseReadTO get(Long id) {
		return Recepisse2RecepisseReadTO.apply(findRecipisse(id));
	}

	/**
	 * List all recepisses
	 *
	 * @return The list of recepisse stored
	 */
	public List<RecepisseReadTO> list() {

		return recepisseRepository.findAll().stream().map(recepisse -> Recepisse2RecepisseReadTO.apply(recepisse))
				.collect(Collectors.toList());
	}

	/**
	 * Update a recepisse
	 *
	 * @param id The id of the recepisse to update
	 * @param recepisseUpdateTO The new recepisse modifications
	 * @return The recepisse updated
	 * @throws ResourceNotFoundException if the given recepisse with that id does not
	 * exist
	 */
	public Recepisse update(Long id, RecepisseUpdateTO recepisseUpdateTO) {

		// find existing recepisse
		Recepisse found = findRecipisse(id);

		// set properties
		found.setNumero(recepisseUpdateTO.getNumero());

		// save update
		return recepisseRepository.save(found);
	}

	/**
	 * Delete a recepisse
	 *
	 * @param id The id of the recepisse to delete
	 * @throws ResourceNotFoundException if the recepisse is not found
	 */
	public void delete(Long id) {
		// find recepisse
		findRecipisse(id);

		// delete
		recepisseRepository.delete(id);
	}

	private Requete findRequete(Long id) {

		// find requete
		Requete requete = requeteRepository.findOne(id);

		if (requete == null) {
			throw new ResourceNotFoundException(
					String.format("The requete with the id %s does not exist", id.toString()));
		}
		return requete;
	}

	private Recepisse findRecipisse(Long id) {

		// find recepisse
		Recepisse recepisse = recepisseRepository.findOne(id);

		if (recepisse == null) {
			throw new ResourceNotFoundException(
					String.format("The recepisse with the id %s does not exist", id.toString()));
		}
		return recepisse;
	}

}
