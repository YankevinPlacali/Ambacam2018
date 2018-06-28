package com.ambacam.transfert.requerants;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class RequerantUpdateTO {
	@NotEmpty(message = "The name must not be empty")
	@NotNull(message = "The name must not be null")
	private String nom;

	private String prenom;

	@NotNull(message = "The birth date must not be null")
	private Date dateNaissance;

	@NotEmpty(message = "The gender must not be empty")
	@NotNull(message = "The gender must not be null")
	private String sexe;

	private String profession;

	@NotEmpty(message = "The place of birth must not be empty")
	@NotNull(message = "The place of birth must not be null")
	private String lieuNaissance;

	private Long paysId;

	public RequerantUpdateTO() {

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public RequerantUpdateTO nom(String nom) {
		this.nom = nom;
		return this;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public RequerantUpdateTO prenom(String prenom) {
		this.prenom = prenom;
		return this;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public RequerantUpdateTO dateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
		return this;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public RequerantUpdateTO sexe(String sexe) {
		this.sexe = sexe;
		return this;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public RequerantUpdateTO profession(String profession) {
		this.profession = profession;
		return this;
	}

	public String getLieuNaissance() {
		return lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public RequerantUpdateTO lieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
		return this;
	}

	public Long getPaysId() {
		return paysId;
	}

	public void setPaysId(Long paysId) {
		this.paysId = paysId;
	}

	public RequerantUpdateTO paysId(Long paysId) {
		this.paysId = paysId;
		return this;
	}

}
