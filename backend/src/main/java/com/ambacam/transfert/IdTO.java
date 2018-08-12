package com.ambacam.transfert;

import javax.validation.constraints.NotNull;

public class IdTO<T> {

	@NotNull(message = "The id must not be null")
	private T id;

	public IdTO() {
	}
	
	public IdTO(T id) {
		this.id = id;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	public IdTO<T> id(T id) {
		this.id = id;
		return this;
	}

}
