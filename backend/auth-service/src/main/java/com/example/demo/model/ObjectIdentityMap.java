package com.example.demo.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ObjectIdentityMap implements Serializable {
	private static final long serialVersionUID = -4760372753273551648L;

	@Id
	@Column(columnDefinition = "bigserial")
	private Long id;

	@Column(columnDefinition = "uuid", nullable = false, unique = true)
	private UUID domainId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getDomainId() {
		return domainId;
	}

	public void setDomainId(UUID domainId) {
		this.domainId = domainId;
	}
}
