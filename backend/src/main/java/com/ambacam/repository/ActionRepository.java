package com.ambacam.repository;

import com.ambacam.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.inject.Named;

@Named
public interface ActionRepository extends JpaRepository<Action, Long> {

	int countByNom(String nom);

}
