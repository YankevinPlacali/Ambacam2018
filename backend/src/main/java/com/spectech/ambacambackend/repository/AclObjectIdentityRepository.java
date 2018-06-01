package com.spectech.ambacambackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spectech.ambacambackend.model.AclObjectIdentity;

public interface AclObjectIdentityRepository extends JpaRepository<AclObjectIdentity, Long> {

}
