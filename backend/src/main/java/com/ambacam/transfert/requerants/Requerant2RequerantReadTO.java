package com.ambacam.transfert.requerants;

import com.ambacam.model.Requerant;

public class Requerant2RequerantReadTO {
	public static RequerantReadTO apply(Requerant in) {
		RequerantReadTO out = new RequerantReadTO();
		out.setId(in.getId());
		out.setNom(in.getNom());
		out.setPrenom(in.getPrenom());
		out.setDateNaissance(in.getDateNaissance());
		out.setCreeLe(in.getCreeLe());
		out.setSexe(in.getSexe());
		out.setProfession(in.getProfession());
		out.setLieuNaissance(in.getLieuNaissance());
		out.setNationalite(in.getNationalite().getNom());
		out.setIdentifier(in.getIdentifier());

		return out;
	}
}
