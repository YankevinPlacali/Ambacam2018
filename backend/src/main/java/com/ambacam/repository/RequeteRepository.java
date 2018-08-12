package com.ambacam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambacam.model.Requete;
import com.ambacam.model.RequeteGroupe;

public interface RequeteRepository extends JpaRepository<Requete, Long> {

    List<Requete> findAllByRequeteGroupe(RequeteGroupe requeteGroupe);

}
