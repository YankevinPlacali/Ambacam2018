import {Statut} from '../models/statut/statut';

export class Object2Statut {
  public static statut: Statut;
  public static statuts: Statut[] = [];

  constructor() {

  }

  public static apply(object): Statut {
    this.statut = {
      id: object.id,
      nom: object.nom,
      description: object.description
    };

    return this.statut;
  }

  public static applyOnArray(objects): Statut[] {
    this.statuts = [];
    objects.forEach(object => {
      this.statuts.push({
        id: object.id,
        nom: object.nom,
        description: object.description
      });
    });

    return this.statuts;
  }
}
