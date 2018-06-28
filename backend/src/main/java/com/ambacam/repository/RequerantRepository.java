package com.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ambacam.model.Requerant;

public interface RequerantRepository extends JpaRepository<Requerant, Long>, JpaSpecificationExecutor<Requerant> {

}
