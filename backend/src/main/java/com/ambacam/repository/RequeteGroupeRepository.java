package com.ambacam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambacam.model.Operateur;
import com.ambacam.model.RequeteGroupe;

public interface RequeteGroupeRepository extends JpaRepository<RequeteGroupe, Long> {

	List<RequeteGroupe> findAllByCreePar(Operateur operateur);

}
