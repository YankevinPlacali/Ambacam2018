package com.ambacam.transfert.requetes;

import java.util.ArrayList;
import java.util.List;

import com.ambacam.model.StatusHistory;

public class RequeteStatusHistoryReadTO {

	private RequeteReadTO requete;
	private List<StatusHistory> history = new ArrayList<>();

	public RequeteReadTO getRequete() {
		return requete;
	}

	public void setRequete(RequeteReadTO requete) {
		this.requete = requete;
	}

	public RequeteStatusHistoryReadTO requete(RequeteReadTO requete) {
		this.requete = requete;
		return this;
	}

	public List<StatusHistory> getHistory() {
		return history;
	}

	public void setHistory(List<StatusHistory> history) {
		this.history = history;
	}

	public RequeteStatusHistoryReadTO history(List<StatusHistory> history) {
		this.history = history;
		return this;
	}

}
