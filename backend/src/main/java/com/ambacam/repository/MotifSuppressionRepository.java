package com.ambacam.repository;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambacam.model.MotifSuppression;

@Named
public interface MotifSuppressionRepository extends JpaRepository<MotifSuppression, Long> {

	int countByNom(String nom);
}
