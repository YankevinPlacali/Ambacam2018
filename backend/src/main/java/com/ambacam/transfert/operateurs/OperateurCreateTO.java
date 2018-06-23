package com.ambacam.transfert.operateurs;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class OperateurCreateTO {

	@NotEmpty(message = "The name must not be empty")
	@NotNull(message = "The name must not be null")
	private String nom;

	private String prenom;

	@NotEmpty(message = "The gender must not be empty")
	@NotNull(message = "The gender must not be null")
	private String sexe;

	@NotNull(message = "The paysId must not be null")
	private Long paysId;

	@NotEmpty(message = "The username must not be empty")
	@NotNull(message = "The username must not be null")
	private String username;

	@NotEmpty(message = "The password must not be empty")
	@NotNull(message = "The password must not be null")
	private String password;

	@NotNull(message = "The creatorId must not be null")
	private Long creatorId;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public Long getPaysId() {
		return paysId;
	}

	public void setPaysId(Long paysId) {
		this.paysId = paysId;
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

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

}
