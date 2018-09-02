import {OperateurStd} from '../../models/operateur/operateurStd';

export class Auth {

  public static operateur: OperateurStd;

  constructor() {

  }

  public static isAuthenticated() {

    const id = sessionStorage.getItem('operateur.id');
    if (id == null) {
      return false;
    } else {
      return true;
    }

  }

  public static store(key: string, value: any) {
    sessionStorage.setItem(key, value);
  }

  public static retrieve(key: string): any {
    return sessionStorage.getItem(key);
  }

  public static revoke(key: string) {
    sessionStorage.removeItem(key);
  }

  public static storeOperateur(operateur: OperateurStd) {
    sessionStorage.setItem('operateur.id', String(operateur.id));
    sessionStorage.setItem('operateur.nom', operateur.nom);
    sessionStorage.setItem('operateur.prenom', operateur.prenom);
    sessionStorage.setItem('operateur.sexe', operateur.sexe);
    sessionStorage.setItem('operateur.nationalite', operateur.nationalite);
    sessionStorage.setItem('operateur.creeParId', String(operateur.creeParId));
    sessionStorage.setItem('operateur.creeLe', String(operateur.creeLe));
  }

  public static getOperateur() {
    const id = sessionStorage.getItem('operateur.id');
    const nom = sessionStorage.getItem('operateur.nom');
    const prenom = sessionStorage.getItem('operateur.prenom');
    const sexe = sessionStorage.getItem('operateur.sexe');
    const nationalite = sessionStorage.getItem('operateur.nationalite');
    const creeParId = sessionStorage.getItem('operateur.creeParId');
    const creeLe = sessionStorage.getItem('operateur.creeLe');

    this.operateur = {
      id: Number(id),
      nom: nom,
      prenom: prenom,
      sexe: sexe,
      nationalite: nationalite,
      creeParId: Number(creeParId),
      creeLe: new Date(creeLe)
    };

    return this.operateur;
  }

  public static reset() {
    sessionStorage.clear();
  }


}
