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
public class Autorite implements Serializable {

	private static final long serialVersionUID = -6019398584857375323L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "The name must not be empty")
	@NotNull(message = "The name must not be null")
	@Column(columnDefinition = "varchar", nullable = false)
	private String nom;

	@Column(columnDefinition = "varchar")
	private String prenom;

	@NotEmpty(message = "The poste must not be empty")
	@NotNull(message = "The poste must not be null")
	@Column(columnDefinition = "varchar", nullable = false)
	private String poste;

	public Autorite() {
	}

	public Autorite(String nom) {
		this.nom = nom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Autorite id(Long id) {
		this.id = id;
		return this;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Autorite nom(String nom) {
		this.nom = nom;
		return this;
	}

	public Autorite prenom(String prenom) {
		this.prenom = prenom;
		return this;
	}

	public Autorite poste(String poste) {
		this.poste = poste;
		return this;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPoste() {
		return poste;
	}

	public void setPoste(String poste) {
		this.poste = poste;
	}

}
