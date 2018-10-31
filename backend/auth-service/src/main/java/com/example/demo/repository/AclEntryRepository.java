package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AclEntry;

public interface AclEntryRepository extends JpaRepository<AclEntry, Long> {

}
