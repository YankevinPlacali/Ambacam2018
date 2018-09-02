import {Operateur} from './operateur';

export class OperateurCreate extends Operateur {

  public paysId: number;
  public creatorId: number;
  public username: string;
  public password: string;

  constructor(nom: string, prenom: string, sexe: string, paysId: number, username: string, password: string, creatorId: number) {
    super(null, nom, prenom, sexe, null);
    this.paysId = paysId;
    this.creatorId = creatorId;
    this.password = password;
    this.username = username;
  }
}
