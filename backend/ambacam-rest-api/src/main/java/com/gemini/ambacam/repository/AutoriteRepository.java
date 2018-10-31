package com.gemini.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.ambacam.model.Autorite;

public interface AutoriteRepository extends JpaRepository<Autorite, Long> {

	int countByNom(String nom);

}
