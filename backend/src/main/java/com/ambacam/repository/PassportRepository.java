package com.ambacam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambacam.model.Passport;

public interface PassportRepository extends JpaRepository<Passport, Long> {

	List<Passport> findAllByRequeteId(Long requeteId);

}
