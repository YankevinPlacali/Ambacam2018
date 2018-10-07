import {Autorite} from '../models/autorite/autorite';

export class Object2Autorite {
  public static autorite: Autorite;
  public static autorites: Autorite[] = [];

  constructor() {

  }

  public static apply(object): Autorite {
    this.autorite = {
      id: object.id,
      nom: object.nom,
      prenom: object.prenom,
      poste: object.poste,
    };

    return this.autorite;
  }

  public static applyOnArray(objects): Autorite[] {
    this.autorites = [];
    objects.forEach(object => {
      this.autorites.push({
        id: object.id,
        nom: object.nom,
        prenom: object.prenom,
        poste: object.poste,
      });
    });

    return this.autorites;
  }
}
