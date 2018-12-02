
export class Recepisse {

  public id: number;

  public recepisseNo: number;

  public requerantId: number;

  public requeteId: number;

  constructor(requerantId: number, requeteId: number) {
    this.requerantId = requerantId;
    this.requeteId = requeteId;
  }

}
