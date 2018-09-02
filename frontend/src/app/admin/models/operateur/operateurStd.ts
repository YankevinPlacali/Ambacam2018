import {Operateur} from './operateur';

export class OperateurStd extends Operateur {

  public nationalite: string;
  public creeParId: number;

  constructor(id: number, nom: string, prenom: string, sexe: string, nationalite: string, creeParId: number, creeLe: Date) {
    super(id, nom, prenom, sexe, creeLe);
    this.nationalite = nationalite;
    this.creeParId = creeParId;
  }
}
