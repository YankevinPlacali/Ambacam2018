import {MotifSuppression} from '../models/motif-suppression/motifSuppression';

export class Object2Motif {
  public static motif: MotifSuppression;
  public static motifs: MotifSuppression[] = [];

  constructor() {

  }

  public static apply(object): MotifSuppression {
    this.motif = {
      id: object.id,
      nom: object.nom,
      description: object.description
    };

    return this.motif;
  }

  public static applyOnArray(objects): MotifSuppression[] {
    this.motifs = [];
    objects.forEach(object => {
      this.motifs.push({
        id: object.id,
        nom: object.nom,
        description: object.description
      });
    });

    return this.motifs;
  }
}
