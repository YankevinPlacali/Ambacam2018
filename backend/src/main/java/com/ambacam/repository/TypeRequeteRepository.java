package com.ambacam.repository;

import com.ambacam.model.TypeRequete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRequeteRepository extends JpaRepository<TypeRequete, Long> {

	int countByNom(String nom);

}
