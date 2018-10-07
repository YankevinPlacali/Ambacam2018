import {Pays} from '../models/pays/pays';

export class Object2Pays {
  public static p: Pays;
  public static pays: Pays[] = [];

  constructor() {

  }

  public static apply(object): Pays {
    this.p = {
      id: object.id,
      nom: object.nom,
      description: object.description
    };

    return this.p;
  }

  public static applyOnArray(objects): Pays[] {
    this.pays = [];
    objects.forEach(object => {
      this.pays.push({
        id: object.id,
        nom: object.nom,
        description: object.description
      });
    });

    return this.pays;
  }
}
