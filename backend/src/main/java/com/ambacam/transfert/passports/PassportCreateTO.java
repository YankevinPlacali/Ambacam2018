package com.ambacam.transfert.passports;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class PassportCreateTO {

	@NotNull(message = "the number must not be null")
	private String numero;

	@NotNull(message = "the date of issue must not be null")
	private Date dateDelivrance;

	@NotNull(message = "the expiration date must not be null")
	private Date dateExpiration;

	@NotNull(message = "the place of issue must not be null")
	private String lieuDelivrance;

	@NotNull(message = "the autoriteId must not be null")
	private Long autoriteId;

	public PassportCreateTO() {

	}

	public PassportCreateTO(String numero, Date dateDelivrance, Date dateExpiration, String lieuDelivrance,
			Long autoriteId) {
		this.numero = numero;
		this.dateDelivrance = dateDelivrance;
		this.dateExpiration = dateExpiration;
		this.lieuDelivrance = lieuDelivrance;
		this.autoriteId = autoriteId;
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

	public Long getAutoriteId() {
		return autoriteId;
	}

	public void setAutoriteId(Long autoriteId) {
		this.autoriteId = autoriteId;
	}
}
