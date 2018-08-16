package com.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambacam.model.StatusRequete;

public interface StatusRequeteRepository extends JpaRepository<StatusRequete, Long> {

	int countByNom(String nom);

	StatusRequete findByNom(String nom);

}
