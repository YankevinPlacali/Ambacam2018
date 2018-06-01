package com.spectech.ambacambackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import com.spectech.ambacambackend.model.EntityTest;

public interface EntityTestRepository extends JpaRepository<EntityTest, Long> {

	@PostFilter("hasPermission(filterObject, 'READ')")
	List<EntityTest> findAll();

	@PostAuthorize("hasPermission(returnObject, 'READ')")
	EntityTest findById(Integer id);

	@SuppressWarnings("unchecked")
	@PreAuthorize("hasPermission(#entityTest, 'WRITE')")
	EntityTest save(@Param("entityTest") EntityTest entityTest);

}
