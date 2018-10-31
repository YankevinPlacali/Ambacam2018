package com.gemini.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.ambacam.model.MotifSuppression;

public interface MotifSuppressionRepository extends JpaRepository<MotifSuppression, Long> {

	int countByNom(String nom);
}