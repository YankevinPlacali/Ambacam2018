package com.gemini.ambacam.transfert.operateurs;

import com.gemini.ambacam.model.Operateur;

public class OperateurCreateTO2Operateur {

	public static Operateur apply(OperateurCreateTO in) {
		Operateur out = new Operateur();
		out.setNom(in.getNom());
		out.setPrenom(in.getPrenom());
		out.setSexe(in.getSexe());
		out.setUsername(in.getUsername());
		out.setPassword(in.getPassword());
		return out;
	}
}
