package com.ambacam.repository;


import com.ambacam.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.inject.Named;

@Named
public interface PaysRepository extends JpaRepository<Pays, Long> {

    int countByNom(String nom);
}
