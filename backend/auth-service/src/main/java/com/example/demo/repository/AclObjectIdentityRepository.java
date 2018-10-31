package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AclObjectIdentity;

public interface AclObjectIdentityRepository extends JpaRepository<AclObjectIdentity, Long> {

}
