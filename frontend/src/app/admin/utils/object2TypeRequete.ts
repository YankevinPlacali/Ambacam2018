import {TypeRequete} from '../models/type-requete/type-requete';

export class Object2TypeRequete {
  public static typeRequete: TypeRequete;
  public static typeRequetes: TypeRequete[] = [];

  constructor() {

  }

  public static apply(object): TypeRequete {
    this.typeRequete = {
      id: object.id,
      nom: object.nom,
      description: object.description
    };

    return this.typeRequete;
  }

  public static applyOnArray(objects): TypeRequete[] {
    this.typeRequetes = [];
    objects.forEach(object => {
      this.typeRequetes.push({
        id: object.id,
        nom: object.nom,
        description: object.description
      });
    });

    return this.typeRequetes;
  }
}
