import {Action} from '../models/action/action';

export class Object2Action {
  public static action: Action;
  public static actions: Action[] = [];

  constructor() {

  }

  public static apply(object): Action {
    this.action = {
      id: object.id,
      nom: object.nom,
      description: object.description
    };

    return this.action;
  }

  public static applyOnArray(objects): Action[] {
    this.actions = [];
    objects.forEach(object => {
      this.actions.push({
        id: object.id,
        nom: object.nom,
        description: object.description
      });
    });

    return this.actions;
  }
}
