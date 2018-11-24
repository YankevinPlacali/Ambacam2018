import {RequeteGroupe} from '../models/requete-groupe/requete-groupe';

export class Object2RequeteGroupe {
  public static requeteGroupe: RequeteGroupe;
  public static requeteGroupes: RequeteGroupe[] = [];

  constructor() {

  }

  public static apply(object): RequeteGroupe {
    this.requeteGroupe = {
      id: object.id,
      nom: object.nom,
      description: object.description,
      status: object.status
    };

    return this.requeteGroupe;
  }

  public static applyOnArray(objects): RequeteGroupe[] {
    this.requeteGroupes = [];
    objects.forEach(object => {
      this.requeteGroupes.push((this.apply(object)));
    });

    return this.requeteGroupes;
  }
}
