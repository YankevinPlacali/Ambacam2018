package com.ambacam.transfert.operateurs;

import com.ambacam.model.Operateur;

public class Operateur2OperateurUpdateTO {

	public static OperateurUpdateTO apply(Operateur in) {
		OperateurUpdateTO out = new OperateurUpdateTO();
		out.setNom(in.getNom());
		out.setPrenom(in.getPrenom());
		out.setSexe(in.getSexe());
		return out;
	}
}
