package com.gemini.ambacam.transfert.operateurs;

import com.gemini.ambacam.model.Operateur;

public class OperateurUpdateTO2Operateur {

	public static Operateur apply(OperateurUpdateTO in) {
		Operateur out = new Operateur();
		out.setNom(in.getNom());
		out.setPrenom(in.getPrenom());
		out.setSexe(in.getSexe());
		return out;
	}
}
