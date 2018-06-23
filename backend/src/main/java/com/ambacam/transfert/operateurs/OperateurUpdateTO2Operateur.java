package com.ambacam.transfert.operateurs;

import com.ambacam.model.Operateur;

public class OperateurUpdateTO2Operateur {

	public static Operateur apply(OperateurUpdateTO in) {
		Operateur out = new Operateur();
		out.setNom(in.getNom());
		out.setPrenom(in.getPrenom());
		out.setSexe(in.getSexe());
		return out;
	}
}
