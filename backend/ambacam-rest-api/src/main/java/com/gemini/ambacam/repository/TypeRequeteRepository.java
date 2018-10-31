package com.gemini.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.ambacam.model.TypeRequete;

public interface TypeRequeteRepository extends JpaRepository<TypeRequete, Long> {

	int countByNom(String nom);

}
