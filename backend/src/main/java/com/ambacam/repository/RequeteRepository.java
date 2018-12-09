package com.ambacam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ambacam.model.Operateur;
import com.ambacam.model.Requerant;
import com.ambacam.model.Requete;
import com.ambacam.model.RequeteGroupe;
import com.ambacam.model.TypeRequete;

public interface RequeteRepository extends JpaRepository<Requete, Long>, JpaSpecificationExecutor<Requete> {

	List<Requete> findAllByRequeteGroupe(RequeteGroupe requeteGroupe);

	int countByRequerantAndType(Requerant requerant, TypeRequete type);

	List<Requete> findAllByOperateur(Operateur operateur);

}
