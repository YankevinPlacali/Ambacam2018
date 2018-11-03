import {Operateur} from "../operateur/operateur";
import {TypeRequete} from "../type-requete/type-requete";
import {Requerant} from "../requerant/requerant";
import {Role} from "../role/role";
import {Statut} from "../statut/statut";

export class Requete {
  public id: number;
  public typeId: number;
  public requerantId: number;
  public statusId: number;
  public operateurId: number;
  public operateur: Operateur;
  public typeRequete: TypeRequete;
  public requerant: Requerant;
  public statusRequete: Statut;
  public creeLe: Date;
  public date: Date;
  public roles : Role[];

  constructor() {

  }
}
