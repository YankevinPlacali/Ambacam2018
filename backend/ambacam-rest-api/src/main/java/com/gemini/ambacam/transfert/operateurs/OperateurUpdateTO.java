package com.gemini.ambacam.transfert.operateurs;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class OperateurUpdateTO {

	@NotEmpty(message = "The name must not be empty")
	@NotNull(message = "The name must not be null")
	private String nom;

	private String prenom;

	@NotEmpty(message = "The gender must not be empty")
	@NotNull(message = "The gender must not be null")
	private String sexe;

	@NotNull(message = "The paysId must not be null")
	private Long paysId;

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

}
