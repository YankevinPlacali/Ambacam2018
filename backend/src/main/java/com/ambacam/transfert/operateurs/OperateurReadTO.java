package com.ambacam.transfert.operateurs;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ambacam.model.Operateur;
import com.ambacam.model.Pays;
import com.ambacam.model.Role;

public class OperateurReadTO {

	private Long id;

	private String nom;

	private String prenom;

	private String sexe;

	private Pays nationalite;

	private Operateur creePar;

	private Date creeLe;

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

	public Operateur getCreePar() {
		return creePar;
	}

	public void setCreePar(Operateur creePar) {
		this.creePar = creePar;
	}

	public Date getCreeLe() {
		return creeLe;
	}

	public void setCreeLe(Date creeLe) {
		this.creeLe = creeLe;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
