package com.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambacam.model.Action;

public interface ActionRepository extends JpaRepository<Action, Long> {

	int countByNom(String nom);

}
