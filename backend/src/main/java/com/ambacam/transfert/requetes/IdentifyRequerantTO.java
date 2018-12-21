package com.ambacam.transfert.requetes;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class IdentifyRequerantTO {

	@NotEmpty(message = "The identifier must not be empty")
	private String identifier;
	@NotNull(message = "The dateNaissance must not be null")
	private Date dateNaissance;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

}
