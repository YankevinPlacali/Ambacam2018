package com.gemini.ambacam.transfert.passports;

import com.gemini.ambacam.model.Passport;

public class Passport2PassportReadTO {
	public static PassportReadTO apply(Passport in) {
		PassportReadTO out = new PassportReadTO();
		out.setId(in.getId());
		out.setNumero(in.getNumero());
		out.setDateDelivrance(in.getDateDelivrance());
		out.setDateExpiration(in.getDateExpiration());
		out.setLieuDelivrance(in.getLieuDelivrance());
		out.setDelivrePar(in.getDelivrePar());
		out.setUrlPhoto(in.getUrlPhoto());

		return out;
	}
}
