import {Recepisse} from '../models/recepisse/recepisse';

export class Object2Recepisse {
  public static recepisse: Recepisse;
  public static recepisses: Recepisse[] = [];

  constructor() {

  }

  public static apply(object): Recepisse {
    this.recepisse = {
      id: object.id,
      recepisseNo: object.recepisseNo,
      requeteId: object.requeteId,
      requerantId: object.requerantId
    };

    return this.recepisse;
  }

  public static applyOnArray(objects): Recepisse[] {
    this.recepisses = [];
    objects.forEach(object => {
      this.recepisses.push({
        id: object.id,
        recepisseNo: object.recepisseNo,
        requeteId: object.requeteId,
        requerantId: object.requerantId
      });
    });

    return this.recepisses;
  }
}
