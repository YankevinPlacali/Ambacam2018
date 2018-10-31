package com.gemini.ambacam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.ambacam.model.Requerant;
import com.gemini.ambacam.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	int countByNom(String nom);

	List<Role> findAllByRequerantsIn(Requerant actual);

}
