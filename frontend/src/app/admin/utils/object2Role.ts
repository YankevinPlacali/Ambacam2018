import {Role} from '../models/role/role';

export class Object2Role {
  public static role: Role;
  public static roles: Role[] = [];

  constructor() {

  }

  public static apply(object): Role {
    this.role = {
      id: object.id,
      nom: object.nom,
      description: object.description
    };

    return this.role;
  }

  public static applyOnArray(objects): Role[] {
    this.roles = [];
    objects.forEach(object => {
      this.roles.push({
        id: object.id,
        nom: object.nom,
        description: object.description
      });
    });

    return this.roles;
  }
}
