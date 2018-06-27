package com.ambacam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ambacam.model.Operateur;

public interface OperateurRepository extends JpaRepository<Operateur, Long>, JpaSpecificationExecutor<Operateur> {

	List<Operateur> findByCreeParId(Long id);
}