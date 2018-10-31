package com.gemini.ambacam.transfert.requetegroupes;

import com.gemini.ambacam.model.RequeteGroupe;
import com.gemini.ambacam.transfert.operateurs.Operateur2OperateurReadTO;

public class RequeteGroupe2RequeteGroupeReadTO {
	public static RequeteGroupeReadTO apply(RequeteGroupe in) {
		RequeteGroupeReadTO out = new RequeteGroupeReadTO().id(in.getId()).nom(in.getNom())
				.description(in.getDescription()).creeLe(in.getCreeLe())
				.creePar(Operateur2OperateurReadTO.apply(in.getCreePar())).status(in.getStatus());

		return out;
	}
}
