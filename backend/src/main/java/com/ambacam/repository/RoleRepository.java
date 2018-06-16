package com.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambacam.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	int countByNom(String nom);

}
