export class TypeRequete {
  public id: number;
  public nom: string;
  public description: string;

  constructor(id: number, nom: string, description: string) {
    this.id = id;
    this.nom = nom;
    this.description = description;
  }
}
