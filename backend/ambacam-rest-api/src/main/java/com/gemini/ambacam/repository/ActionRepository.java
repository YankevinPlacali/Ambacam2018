package com.gemini.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.ambacam.model.Action;

public interface ActionRepository extends JpaRepository<Action, Long> {

	int countByNom(String nom);

}
