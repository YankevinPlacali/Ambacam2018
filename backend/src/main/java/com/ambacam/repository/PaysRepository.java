package com.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambacam.model.Pays;

public interface PaysRepository extends JpaRepository<Pays, Long> {

	int countByNom(String nom);

}
