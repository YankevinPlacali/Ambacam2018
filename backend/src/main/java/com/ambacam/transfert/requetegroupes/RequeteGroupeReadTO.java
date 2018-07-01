package com.ambacam.transfert.requetegroupes;

import java.util.Date;

import com.ambacam.transfert.operateurs.OperateurReadTO;

public class RequeteGroupeReadTO {

	private Long id;

	private String nom;

	private String description;

	private Date creeLe;

	private OperateurReadTO creePar;

	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequeteGroupeReadTO id(Long id) {
		this.id = id;
		return this;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public RequeteGroupeReadTO nom(String nom) {
		this.nom = nom;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RequeteGroupeReadTO description(String description) {
		this.description = description;
		return this;
	}

	public Date getCreeLe() {
		return creeLe;
	}

	public void setCreeLe(Date creeLe) {
		this.creeLe = creeLe;
	}

	public RequeteGroupeReadTO creeLe(Date creeLe) {
		this.creeLe = creeLe;
		return this;
	}

	public OperateurReadTO getCreePar() {
		return creePar;
	}

	public void setCreePar(OperateurReadTO creePar) {
		this.creePar = creePar;
	}

	public RequeteGroupeReadTO creePar(OperateurReadTO creePar) {
		this.creePar = creePar;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RequeteGroupeReadTO status(String status) {
		this.status = status;
		return this;
	}
}
