package com.gemini.ambacam.transfert.requerants;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class RequerantCreateTO {
	@NotEmpty(message = "The name must not be empty")
	@NotNull(message = "The name must not be null")
	private String nom;

	private String prenom;

	@NotNull(message = "The birth date must not be null")
	private Date dateNaissance;

	@NotNull(message = "The creatorId must not be null")
	private Long creatorId;

	@NotEmpty(message = "The gender must not be empty")
	@NotNull(message = "The gender must not be null")
	private String sexe;

	private String profession;

	@NotEmpty(message = "The place of birth must not be empty")
	@NotNull(message = "The place of birth must not be null")
	private String lieuNaissance;

	@NotNull(message = "The paysId must not be null")
	private Long paysId;

	public RequerantCreateTO() {

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public RequerantCreateTO nom(String nom) {
		this.nom = nom;
		return this;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public RequerantCreateTO prenom(String prenom) {
		this.prenom = prenom;
		return this;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public RequerantCreateTO dateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
		return this;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public RequerantCreateTO creatorId(Long creatorId) {
		this.creatorId = creatorId;
		return this;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public RequerantCreateTO sexe(String sexe) {
		this.sexe = sexe;
		return this;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public RequerantCreateTO profession(String profession) {
		this.profession = profession;
		return this;
	}

	public String getLieuNaissance() {
		return lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public RequerantCreateTO lieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
		return this;
	}

	public Long getPaysId() {
		return paysId;
	}

	public void setPaysId(Long paysId) {
		this.paysId = paysId;
	}

	public RequerantCreateTO paysId(Long paysId) {
		this.paysId = paysId;
		return this;
	}

}
