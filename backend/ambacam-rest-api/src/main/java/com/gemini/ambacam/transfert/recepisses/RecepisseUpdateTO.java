package com.gemini.ambacam.transfert.recepisses;

import javax.validation.constraints.NotNull;

public class RecepisseUpdateTO {
	@NotNull(message = "The number must not be null")
	private Long numero;

	public RecepisseUpdateTO() {
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public RecepisseUpdateTO numero(Long numero) {
		this.numero = numero;
		return this;
	}

}
