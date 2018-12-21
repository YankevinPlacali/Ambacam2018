import {StatusHistory} from '../models/statusHistory';

export class Object2StatusHistory {
  public static statusHistory: StatusHistory;
  public static statusHistories: StatusHistory[] = [];

  constructor() {

  }

  public static apply(object): StatusHistory {
    this.statusHistory = {
      id: object.id,
      className: object.className,
      entityId: object.entityId,
      name: object.name,
      creeLe: object.creeLe
    };

    return this.statusHistory;
  }

  public static applyOnArray(objects): StatusHistory[] {
    this.statusHistories = [];
    objects.forEach(object => {
      this.statusHistories.push(Object2StatusHistory.apply(object));
    });

    return this.statusHistories;
  }
}
