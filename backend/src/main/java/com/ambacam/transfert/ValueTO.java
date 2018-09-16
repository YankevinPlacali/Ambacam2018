package com.ambacam.transfert;

import javax.validation.constraints.NotNull;

public class ValueTO<T> {

	@NotNull(message = "The value must not be null")
	private T value;

	public ValueTO() {

	}

	public ValueTO(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public ValueTO<T> value(T value) {
		this.value = value;
		return this;
	}

}
