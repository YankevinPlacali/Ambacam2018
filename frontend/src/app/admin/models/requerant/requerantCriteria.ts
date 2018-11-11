import {SearchCriteria} from '../searchCriteria';

export class RequerantCriteria extends SearchCriteria {
  public dateNaissance: Date;
  public creatorId: number;
  public creeLeBefore: Date;
  public creeLeAfter: Date;

  constructor(keyword: string, dateNaissance: Date, creatorId: number, creeLeBefore: Date, creeLeAfter: Date) {
    super();
    this.keyword = keyword;
    this.dateNaissance = dateNaissance;
    this.creatorId = creatorId;
    this.creeLeBefore = creeLeBefore;
    this.creeLeAfter = creeLeAfter;
  }
}
