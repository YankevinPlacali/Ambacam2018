import {Operateur} from '../models/operateur/operateur';

export class Object2Operateur {
  public static operateur: Operateur;

  constructor() {

  }

  public static apply(object): Operateur {
    if (object.creePar !== null) {
      this.operateur = {
        id: object.id,
        nom: object.nom,
        prenom: object.prenom,
        sexe: object.sexe,
        nationalite: object.nationalite.nom,
        creeParId: object.creePar.id,
        creeLe: new Date(object.creeLe)
      };
    } else {
      this.operateur = {
        id: object.id,
        nom: object.nom,
        prenom: object.prenom,
        sexe: object.sexe,
        nationalite: object.nationalite.nom,
        creeParId: null,
        creeLe: new Date(object.creeLe)
      };
    }

    return this.operateur;
  }
}
