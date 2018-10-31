package com.gemini.ambacam.transfert.recepisses;

import javax.validation.constraints.NotNull;

public class RecepisseCreateTO {
	@NotNull(message = "The number must not be null")
	private Long numero;

	public RecepisseCreateTO() {
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public RecepisseCreateTO numero(Long numero) {
		this.numero = numero;
		return this;
	}
}
