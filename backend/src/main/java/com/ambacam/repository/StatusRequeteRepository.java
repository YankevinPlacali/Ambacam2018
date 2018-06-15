package com.ambacam.repository;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambacam.model.StatusRequete;

@Named
public interface StatusRequeteRepository extends JpaRepository<StatusRequete, Long> {

	int countByNom(String nom);

}
