package com.gemini.ambacam.transfert.operateurs;

import com.gemini.ambacam.model.Operateur;

public class Operateur2OperateurReadTO {

	public static OperateurReadTO apply(Operateur in) {
		OperateurReadTO out = new OperateurReadTO();
		out.setId(in.getId());
		out.setNom(in.getNom());
		out.setPrenom(in.getPrenom());
		out.setSexe(in.getSexe());
		out.setNationalite(in.getNationalite());
		out.setRoles(in.getRoles());
		out.setCreeLe(in.getCreeLe());
		out.setCreePar(in.getCreePar());
		return out;
	}
}
