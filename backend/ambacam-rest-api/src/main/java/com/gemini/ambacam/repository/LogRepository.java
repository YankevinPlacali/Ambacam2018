package com.gemini.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gemini.ambacam.model.Log;

public interface LogRepository extends JpaRepository<Log, Long>, JpaSpecificationExecutor<Log> {

}
