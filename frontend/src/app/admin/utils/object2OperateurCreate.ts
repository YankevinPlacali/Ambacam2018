import {OperateurCreate} from '../models/operateur/operateurCreate';

export class Object2OperateurCreate {
  public static operateurCreate: OperateurCreate;

  constructor() {

  }

  public static apply(object): OperateurCreate {
    if (object.creePar !== null) {
      this.operateurCreate = {
        id: object.id,
        nom: object.nom,
        prenom: object.prenom,
        sexe: object.sexe,
        paysId: object.nationalite.id,
        username: object.username,
        password: object.password,
        creatorId: object.creePar.id,
        creeLe: null
      };
    } else {
      this.operateurCreate = {
        id: object.id,
        nom: object.nom,
        prenom: object.prenom,
        sexe: object.sexe,
        paysId: object.nationalite.id,
        username: object.username,
        password: object.password,
        creatorId: null,
        creeLe: null
      };
    }

    return this.operateurCreate;
  }
}
