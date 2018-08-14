package com.ambacam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ambacam.model.Requete;
import com.ambacam.model.RequeteGroupe;

public interface RequeteRepository extends JpaRepository<Requete, Long>, JpaSpecificationExecutor<Requete> {

    List<Requete> findAllByRequeteGroupe(RequeteGroupe requeteGroupe);

}
