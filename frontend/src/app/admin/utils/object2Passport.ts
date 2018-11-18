import {PassportRead} from '../models/passport/passport';
import {Object2Autorite} from './object2Autorite';

export class Object2PassportRead {
  public static passport: PassportRead;
  public static passports: PassportRead [];

  constructor() {

  }

  public static apply(object): PassportRead {
    this.passport = new PassportRead(
      object.id,
      object.numero,
      object.dateDelivrance,
      object.dateExpiration,
      object.lieuDelivrance,
      Object2Autorite.apply(object.delivrePar),
      object.urlPhoto
    );

    return this.passport;
  }

  public static applyOnArray(objects): PassportRead[] {
    this.passports = [];
    objects.forEach(object => {
      this.passports.push(this.apply(object));
    });

    return this.passports;
  }
}

