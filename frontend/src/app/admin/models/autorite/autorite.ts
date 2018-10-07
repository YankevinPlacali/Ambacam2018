export class Autorite {
  public id: number;
  public nom: string;
  public prenom: string;
  public poste: string;

  constructor(id: number, nom: string, prenom: string, poste: string,) {
    this.id = id;
    this.nom = nom;
    this.prenom = prenom;
    this.poste = poste;
  }
}
