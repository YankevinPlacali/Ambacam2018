package com.gemini.ambacam.transfert.operateurs;

import com.gemini.ambacam.model.Operateur;

public class Operateur2OperateurUpdateTO {

	public static OperateurUpdateTO apply(Operateur in) {
		OperateurUpdateTO out = new OperateurUpdateTO();
		out.setNom(in.getNom());
		out.setPrenom(in.getPrenom());
		out.setSexe(in.getSexe());
		return out;
	}
}
