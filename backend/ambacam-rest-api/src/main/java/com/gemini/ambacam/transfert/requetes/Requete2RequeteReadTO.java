package com.gemini.ambacam.transfert.requetes;

import com.gemini.ambacam.model.Requete;
import com.gemini.ambacam.transfert.operateurs.Operateur2OperateurReadTO;
import com.gemini.ambacam.transfert.requerants.Requerant2RequerantReadTO;

public class Requete2RequeteReadTO {
    public static RequeteReadTO apply(Requete in) {

        RequeteReadTO out = new RequeteReadTO();

        out.setId(in.getId());
        out.setType(in.getType());
        out.setStatus(in.getStatus());
        out.setDate(in.getCreeLe());
        out.setRequerant(Requerant2RequerantReadTO.apply(in.getRequerant()));
        out.setOperateur(Operateur2OperateurReadTO.apply(in.getOperateur()));

        return out;
    }
}
