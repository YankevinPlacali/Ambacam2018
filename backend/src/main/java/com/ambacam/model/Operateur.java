package com.ambacam.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Operateur extends AuditingCommonEntity implements Serializable {
	private static final long serialVersionUID = 7327701821300391989L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "The name must not be empty")
	@NotNull(message = "The name must not be null")
	@Column(columnDefinition = "varchar", nullable = false)
	private String nom;

	@Column(columnDefinition = "varchar")
	private String prenom;

	@NotEmpty(message = "The gender must not be empty")
	@NotNull(message = "The gender must not be null")
	@Column(columnDefinition = "varchar", nullable = false)
	private String sexe;

	@ManyToOne
	@JoinColumn(name = "nationalite_id", nullable = false, updatable = true)
	private Pays nationalite;

	@NotEmpty(message = "The username must not be empty")
	@NotNull(message = "The username must not be null")
	@Column(columnDefinition = "varchar", nullable = false)
	private String username;

	@NotEmpty(message = "The password must not be empty")
	@NotNull(message = "The password must not be null")
	@Column(columnDefinition = "varchar", nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "cree_par_id", nullable = true, updatable = true)
	private Operateur creePar;

	@ManyToMany
	@JoinTable(name = "operateur_role", joinColumns = @JoinColumn(name = "operateur_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, updatable = false))
	private Set<Role> roles = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Operateur nom(String nom) {
		this.nom = nom;
		return this;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public Pays getNationalite() {
		return nationalite;
	}

	public void setNationalite(Pays nationalite) {
		this.nationalite = nationalite;
	}

	public Operateur nationalite(Pays nationalite) {
		this.nationalite = nationalite;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Operateur getCreePar() {
		return creePar;
	}

	public void setCreePar(Operateur creePar) {
		this.creePar = creePar;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
