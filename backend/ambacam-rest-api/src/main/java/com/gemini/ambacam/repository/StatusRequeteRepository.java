package com.gemini.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.ambacam.model.StatusRequete;

public interface StatusRequeteRepository extends JpaRepository<StatusRequete, Long> {

	int countByNom(String nom);

	StatusRequete findByNom(String nom);

}
