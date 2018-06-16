package com.ambacam.service;

import com.ambacam.exception.ResourceBadRequestException;
import com.ambacam.exception.ResourceNotFoundException;
import com.ambacam.model.Action;
import com.ambacam.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ActionService {
	@Autowired
	private ActionRepository actionRepository;

	/**
	 * Create an action
	 * 
	 * @param action The new action to create
	 * 
	 * @return The new action created
	 * @throws ResourceBadRequestException if an action with the name already exist
	 */
	public Action create(Action action) {

		checkConsistency(action);

		action.setId(null);

		return actionRepository.save(action);
	}

	/**
	 * Get an action
	 * 
	 * @param id The id of the Action you want to get
	 * 
	 * @return The action found
	 * 
	 * @throws ResourceNotFoundException if the action does not exist
	 */
	public Action get(Long id) {
		return findAction(id);
	}

	/**
	 * List all actions
	 * 
	 * @return The list of action stored
	 */
	public List<Action> list() {

		return actionRepository.findAll();
	}

	/**
	 * Update an action
	 * 
	 * @param id The id of the action to update
	 * 
	 * @param action The new action modifications
	 * 
	 * @return The action updated
	 * 
	 * @throws ResourceNotFoundException if the action is not found
	 * @throws ResourceBadRequestException if an action with the name already exist
	 */
	public Action update(Long id, Action action) {

		// find action
		Action found = findAction(id);

		if (!found.getNom().equals(action.getNom())) {
			checkConsistency(action);
		}

		action.setId(id);
		return actionRepository.save(action);
	}

	/**
	 * Delete an action
	 * 
	 * @param id The id of the action to delete
	 * 
	 * @throws ResourceNotFoundException if the action is not found
	 */
	public void delete(Long id) {
		// find action
		findAction(id);
		actionRepository.delete(id);
	}

	private Action findAction(Long id) {
		// find action
		Action action = actionRepository.findOne(id);
		if (action == null) {
			throw new ResourceNotFoundException("The action " + id.toString() + " does not exist");
		}

		return action;
	}

	private void checkConsistency(Action action) {

		if (actionRepository.countByNom(action.getNom()) > 0) {
			throw new ResourceBadRequestException(
					String.format("An action with a name '%s' exist already", action.getNom()));
		}
	}

}
