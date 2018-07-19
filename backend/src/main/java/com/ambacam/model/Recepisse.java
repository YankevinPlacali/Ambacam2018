package com.ambacam.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Recepisse implements Serializable {

	private static final long serialVersionUID = -5035070147849340620L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long numero;

	@ManyToOne
	@JoinColumn(name = "requete_id")
	private Requete requete;

	public Recepisse numero(Long numero) {
		this.numero = numero;
		return this;
	}

	public Recepisse requete(Requete requete) {
		this.requete = requete;
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Requete getRequete() {
		return requete;
	}

	public void setRequete(Requete requete) {
		this.requete = requete;
	}
}
