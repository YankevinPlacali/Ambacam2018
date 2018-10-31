package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AclSid;

public interface AclSidRepository extends JpaRepository<AclSid, Long> {

}
