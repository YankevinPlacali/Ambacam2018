package com.ambacam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = -8019398584857375323L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "The nom must not be empty")
	@NotNull(message = "The nom must not be null")
	@Column(columnDefinition = "VARCHAR", nullable = false)
	private String nom;

	@Column(columnDefinition = "varchar")
	private String description;

	public Role() {
	}

	public Role(String nom) {
		this.nom = nom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role id(Long id) {
		this.id = id;
		return this;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Role nom(String nom) {
		this.nom = nom;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Role description(String description) {
		this.description = description;
		return this;
	}
}