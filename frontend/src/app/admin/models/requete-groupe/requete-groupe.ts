export class RequeteGroupe {
  public id: number;
  public nom: string;
  public description: string;
  public status: string;

  constructor(id: number, nom: string, description: string) {
    this.id = id;
    this.nom = nom;
    this.description = description;
  }
}
