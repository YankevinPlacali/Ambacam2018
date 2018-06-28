package com.ambacam.model;

import java.io.Serializable;
import java.util.Date;
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
public class Requerant extends AuditingCommonEntity implements Serializable {

	private static final long serialVersionUID = 8465780599548569420L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "The name must not be empty")
	@NotNull(message = "The name must not be null")
	@Column(columnDefinition = "varchar", nullable = false)
	private String nom;

	@Column(columnDefinition = "varchar")
	private String prenom;

	@NotNull(message = "The birth date must not be null")
	private Date dateNaissance;

	@ManyToOne
	@JoinColumn(name = "cree_par_id", nullable = false, updatable = true)
	private Operateur creePar;

	@ManyToMany
	@JoinTable(name = "requerant_role", joinColumns = @JoinColumn(name = "requerant_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, updatable = false))
	private Set<Role> roles = new HashSet<>();

	@NotEmpty(message = "The gender must not be empty")
	@NotNull(message = "The gender must not be null")
	@Column(columnDefinition = "varchar", nullable = false)
	private String sexe;

	@Column(columnDefinition = "varchar")
	private String profession;

	@NotEmpty(message = "The place of birth must not be empty")
	@NotNull(message = "The place of birth must not be null")
	@Column(columnDefinition = "varchar", nullable = false)
	private String lieuNaissance;

	@ManyToOne
	@JoinColumn(name = "nationalite_id", nullable = false, updatable = true)
	private Pays nationalite;

	public Requerant() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Requerant id(Long id) {
		this.id = id;
		return this;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Requerant nom(String nom) {
		this.nom = nom;
		return this;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Requerant prenom(String prenom) {
		this.prenom = prenom;
		return this;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Requerant dateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
		return this;
	}

	public Operateur getCreePar() {
		return creePar;
	}

	public void setCreePar(Operateur creePar) {
		this.creePar = creePar;
	}

	public Requerant creePar(Operateur creePar) {
		this.creePar = creePar;
		return this;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Requerant roles(Set<Role> roles) {
		this.roles = roles;
		return this;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public Requerant sexe(String sexe) {
		this.sexe = sexe;
		return this;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Requerant profession(String profession) {
		this.profession = profession;
		return this;
	}

	public String getLieuNaissance() {
		return lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public Requerant lieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
		return this;
	}

	public Pays getNationalite() {
		return nationalite;
	}

	public void setNationalite(Pays nationalite) {
		this.nationalite = nationalite;
	}

	public Requerant nationalite(Pays nationalite) {
		this.nationalite = nationalite;
		return this;
	}

}
