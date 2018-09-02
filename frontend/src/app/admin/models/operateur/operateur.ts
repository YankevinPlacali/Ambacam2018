export class Operateur {
  public id: number;

  public nom: string;
  public prenom: string;
  public sexe: string;
  public creeLe: Date;

  constructor(id: number, nom: string, prenom: string, sexe: string, creeLe: Date) {
    this.id = id;
    this.nom = nom;
    this.prenom = prenom;
    this.sexe = sexe;
    this.creeLe = creeLe;
  }
}
