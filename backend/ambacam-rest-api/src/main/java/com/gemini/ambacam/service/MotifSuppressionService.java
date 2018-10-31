package com.gemini.ambacam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gemini.ambacam.exception.ResourceBadRequestException;
import com.gemini.ambacam.exception.ResourceNotFoundException;
import com.gemini.ambacam.model.MotifSuppression;
import com.gemini.ambacam.repository.MotifSuppressionRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class MotifSuppressionService {
	@Autowired
	private MotifSuppressionRepository motifSuppressionRepository;

	/**
	 * Create a motifSuppression
	 * 
	 * @param motifSuppression
	 * 
	 * @return
	 * @throws ResourceBadRequestException if a motifSuppression with the name already
	 * exist
	 */
	public MotifSuppression create(MotifSuppression motifSuppression) {

		checkConsistency(motifSuppression);

		motifSuppression.setId(null);

		return motifSuppressionRepository.save(motifSuppression);
	}

	/**
	 * Get a motifSuppression
	 * 
	 * @param motifSuppression
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the motifSuppression does not exist
	 */
	public MotifSuppression get(Long id) {
		return findMotifSuppression(id);
	}

	/**
	 * List all motifSuppressions
	 * 
	 * @return
	 */
	public List<MotifSuppression> list() {

		return motifSuppressionRepository.findAll();
	}

	/**
	 * Update a motifSuppression
	 * 
	 * @param id
	 * 
	 * @param update
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the motifSuppression is not found
	 * @throws ResourceBadRequestException if a motifSuppression with the name already
	 * exist
	 */
	public MotifSuppression update(Long id, MotifSuppression update) {

		// find motifSuppression
		MotifSuppression found = findMotifSuppression(id);

		if (!found.getNom().equals(update.getNom())) {
			checkConsistency(update);
		}

		update.setId(id);
		return motifSuppressionRepository.save(update);
	}

	/**
	 * Delete a motifSuppression
	 * 
	 * @param motifSuppression
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the motifSuppression is not found
	 */
	public void delete(Long id) {
		// find motifSuppression
		findMotifSuppression(id);
		motifSuppressionRepository.delete(id);
	}

	private MotifSuppression findMotifSuppression(Long id) {
		// find motifSuppression
		MotifSuppression found = motifSuppressionRepository.findOne(id);
		if (found == null) {
			throw new ResourceNotFoundException("The motifSuppression " + id.toString() + " does not exist");
		}

		return found;
	}

	private void checkConsistency(MotifSuppression motifSuppression) {

		if (motifSuppressionRepository.countByNom(motifSuppression.getNom()) > 0) {
			throw new ResourceBadRequestException(
					String.format("A motifSuppression with a name '%s' exist already", motifSuppression.getNom()));
		}
	}
}
