package com.ambacam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambacam.exception.ResourceBadRequestException;
import com.ambacam.exception.ResourceNotFoundException;
import com.ambacam.model.Role;
import com.ambacam.repository.RoleRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Create a role
	 * 
	 * @param role
	 * 
	 * @return
	 * @throws ResourceBadRequestException if a role with the name already exist
	 */
	public Role create(Role role) {

		checkConsistency(role);

		role.setId(null);

		return roleRepository.save(role);
	}

	/**
	 * Get a role
	 * 
	 * @param role
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the role does not exist
	 */
	public Role get(Long id) {
		return findRole(id);
	}

	/**
	 * List all roles
	 * 
	 * @return
	 */
	public List<Role> list() {

		return roleRepository.findAll();
	}

	/**
	 * Update a role
	 * 
	 * @param id
	 * 
	 * @param update
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the role is not found
	 * @throws ResourceBadRequestException if a role with the name already exist
	 */
	public Role update(Long id, Role update) {

		// find role
		Role role = findRole(id);

		if (!role.getNom().equals(update.getNom())) {
			checkConsistency(update);
		}

		update.setId(id);
		return roleRepository.save(update);
	}

	/**
	 * Delete a role
	 * 
	 * @param role
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the role is not found
	 */
	public void delete(Long id) {
		// find role
		findRole(id);
		roleRepository.delete(id);
	}

	private Role findRole(Long id) {
		// find role
		Role role = roleRepository.findOne(id);
		if (role == null) {
			throw new ResourceNotFoundException("The role " + id.toString() + " does not exist");
		}

		return role;
	}

	private void checkConsistency(Role role) {

		if (roleRepository.countByNom(role.getNom()) > 0) {
			throw new ResourceBadRequestException(
					String.format("A role with a name '%s' exist already", role.getNom()));
		}
	}

}
