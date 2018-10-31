package com.gemini.ambacam.transfert.passports;

import java.util.Date;

import com.gemini.ambacam.model.Autorite;

public class PassportReadTO {

	private Long id;

	private String numero;

	private Date dateDelivrance;

	private Date dateExpiration;

	private String lieuDelivrance;

	private Autorite delivrePar;

	private String urlPhoto;

	public PassportReadTO() {

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
}
