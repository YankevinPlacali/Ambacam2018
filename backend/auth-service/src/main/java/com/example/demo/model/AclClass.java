package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AclClass implements Serializable {
	private static final long serialVersionUID = 9069478365168880316L;

	@Id
	@GeneratedValue
	@Column(columnDefinition = "bigserial")
	private Long id;

	@Column(name = "class", columnDefinition = "varchar", nullable = false, unique = true)
	private String clazz;

	public AclClass() {

	}

	public AclClass(String clazz) {
		this.clazz = clazz;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

}
