package com.spectech.ambacambackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spectech.ambacambackend.model.AclEntry;

public interface AclEntryRepository extends JpaRepository<AclEntry, Long> {

}
