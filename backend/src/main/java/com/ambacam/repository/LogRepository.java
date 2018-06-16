package com.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ambacam.model.Log;

public interface LogRepository extends JpaRepository<Log, Long>, JpaSpecificationExecutor<Log> {

}
