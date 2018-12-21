import {RequeteStatusHistoryRead} from '../models/requeteStatusHistoryRead';
import {Object2Requete} from '../../admin/utils/object2Requete';
import {Object2StatusHistory} from './object2StatusHistory';


export class Object2RequeteStatusHistoryRead {
  public static requeteStatusHistory: RequeteStatusHistoryRead;
  public static requeteStatusHistories: RequeteStatusHistoryRead[] = [];

  constructor() {

  }

  public static apply(object): RequeteStatusHistoryRead {
    this.requeteStatusHistory = {
      requete: Object2Requete.apply(object.requete),
      history: Object2StatusHistory.applyOnArray(object.history)
    };

    return this.requeteStatusHistory;
  }

  public static applyOnArray(objects): RequeteStatusHistoryRead[] {
    this.requeteStatusHistories = [];
    objects.forEach(object => {
      this.requeteStatusHistories.push(Object2RequeteStatusHistoryRead.apply(object));
    });

    return this.requeteStatusHistories;
  }
}
