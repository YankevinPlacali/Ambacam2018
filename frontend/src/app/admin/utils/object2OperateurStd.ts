import {OperateurStd} from '../models/operateur/operateurStd';
import {Role} from '../models/role/role';

export class Object2OperateurStd {
  public static operateurStd: OperateurStd;
  public static operateursStds: OperateurStd[] = [];

  constructor() {

  }

  public static apply(object): OperateurStd {
    if (object.creePar !== null) {
      this.operateurStd = {
        id: object.id,
        nom: object.nom,
        prenom: object.prenom,
        sexe: object.sexe,
        nationalite: object.nationalite.nom,
        creeParId: object.creePar.id,
        creeLe: new Date(object.creeLe)
      };
    } else {
      this.operateurStd = {
        id: object.id,
        nom: object.nom,
        prenom: object.prenom,
        sexe: object.sexe,
        nationalite: object.nationalite.nom,
        creeParId: null,
        creeLe: new Date(object.creeLe)
      };
    }

    return this.operateurStd;
  }

  public static applyOnArray(objects): OperateurStd[] {
    this.operateursStds = [];
    objects.forEach(object => {
      this.operateursStds.push({
        id: object.id,
        nom: object.nom,
        prenom: object.prenom,
        sexe: object.sexe,
        nationalite: object.nationalite.nom,
        creeParId: object.creeParId,
        creeLe: object.creeLe
      });
    });

    return this.operateursStds;
  }
}
