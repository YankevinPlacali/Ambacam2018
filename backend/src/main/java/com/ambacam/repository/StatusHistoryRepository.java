package com.ambacam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambacam.model.StatusHistory;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {

	List<StatusHistory> findByClassNameAndEntityIdOrderByCreeLeAsc(String className, Long entityId);

}
