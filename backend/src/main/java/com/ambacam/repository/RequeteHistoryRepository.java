package com.ambacam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambacam.model.StatusHistory;

public interface RequeteHistoryRepository extends JpaRepository<StatusHistory, Long> {

}
