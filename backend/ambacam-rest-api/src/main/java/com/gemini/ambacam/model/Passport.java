package com.gemini.ambacam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Passport implements Serializable {

	private static final long serialVersionUID = -7682152333891800618L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "the number must not be null")
	@Column(columnDefinition = "varchar")
	private String numero;

	@NotNull(message = "the date of issue must not be null")
	private Date dateDelivrance;

	@NotNull(message = "the expiration date must not be null")
	private Date dateExpiration;

	@NotNull(message = "the place of issue must not be null")
	@Column(columnDefinition = "varchar")
	private String lieuDelivrance;

	@ManyToOne
	@JoinColumn(name = "autorite_id", nullable = false)
	private Autorite delivrePar;

	@Column(columnDefinition = "varchar")
	private String urlPhoto;

	@ManyToOne
	@JoinColumn(name = "requete_id", nullable = false)
	private Requete requete;

	public Passport() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getDateDelivrance() {
		return dateDelivrance;
	}

	public void setDateDelivrance(Date dateDelivrance) {
		this.dateDelivrance = dateDelivrance;
	}

	public Date getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public String getLieuDelivrance() {
		return lieuDelivrance;
	}

	public void setLieuDelivrance(String lieuDelivrance) {
		this.lieuDelivrance = lieuDelivrance;
	}

	public Autorite getDelivrePar() {
		return delivrePar;
	}

	public void setDelivrePar(Autorite delivrePar) {
		this.delivrePar = delivrePar;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}

	public Requete getRequete() {
		return requete;
	}

	public void setRequete(Requete requete) {
		this.requete = requete;
	}
}
