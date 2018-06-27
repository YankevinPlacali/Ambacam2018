package com.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambacam.model.MotifSuppression;

public interface MotifSuppressionRepository extends JpaRepository<MotifSuppression, Long> {

	int countByNom(String nom);
}