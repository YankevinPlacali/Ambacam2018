import {Operateur} from '../operateur/operateur';
import {TypeRequete} from '../type-requete/type-requete';
import {Requerant} from '../requerant/requerant';
import {Role} from '../role/role';
import {Statut} from '../statut/statut';
import {Autorite} from '../autorite/autorite';

export class PassportStd {
  public numero: string;
  public dateDelivrance: Date;
  public dateExpiration: Date;
  public lieuDelivrance: string;

  constructor(numero: string, dateDelivrance: Date, dateExpiration: Date, lieuDelivrance: string) {
    this.numero = numero;
    this.dateDelivrance = dateDelivrance;
    this.dateExpiration = dateExpiration;
    this.lieuDelivrance = lieuDelivrance;
  }
}

export class PassportRead extends PassportStd {
  public id: string;
  public delivrePar: Autorite;
  public urlPhoto: string;

  constructor(id: string, numero: string, dateDelivrance: Date, dateExpiration: Date, lieuDelivrance: string, delivrePar: Autorite, urlPhoto: string) {
    super(numero, dateDelivrance, dateExpiration, lieuDelivrance);
    this.id = id;
    this.delivrePar = delivrePar;
    this.urlPhoto = urlPhoto;
  }
}

export class PassportWrite extends PassportStd {
  public autoriteId: number;

  constructor(numero: string, dateDelivrance: Date, dateExpiration: Date, lieuDelivrance: string, autoriteId: number) {
    super(numero, dateDelivrance, dateExpiration, lieuDelivrance);
    this.autoriteId = autoriteId;
  }
}
