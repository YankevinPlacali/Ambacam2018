package com.gemini.ambacam.transfert.operateurs;

import com.gemini.ambacam.model.Operateur;

public class Operateur2OperateurCreatedTO {

	public static OperateurCreateTO apply(Operateur in) {
		OperateurCreateTO out = new OperateurCreateTO();
		out.setNom(in.getNom());
		out.setPrenom(in.getPrenom());
		out.setSexe(in.getSexe());
		out.setUsername(in.getUsername());
		out.setPassword(in.getPassword());
		return out;
	}
}
