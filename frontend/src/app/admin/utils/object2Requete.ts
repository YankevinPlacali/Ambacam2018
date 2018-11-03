import {Requerant} from "../models/requerant/requerant";
import {DateUtils} from "./dateUtils";
import {Requete} from "../models/requete/requete";
import {Object2Statut} from "./object2Statut";
import {Object2TypeRequete} from "./object2TypeRequete";
import {Object2Requerant} from "./object2Requerant";
import {Object2OperateurStd} from "./object2OperateurStd";

export class Object2Requete {
  public static requete: Requete;
  public static requetes: Requete [];

  constructor() {

  }

  public static apply(object): Requete {
    this.requete = new Requete();

    this.requete.id = object.id;
    this.requete.typeRequete = Object2TypeRequete.apply(object.type);
    this.requete.statusRequete = Object2Statut.apply(object.status);
    this.requete.date = new Date(object.date);
    this.requete.requerant = Object2Requerant.apply(object.requerant);
    this.requete.operateur = Object2OperateurStd.apply(object.operateur);
    this.requete.creeLe = new Date(object.creeLe);

    return this.requete;
  }

  public static  applyOnArray(objects): Requete[] {
    this.requetes = [];
    objects.forEach(object => {
      this.requetes.push(this.apply(object));
    });

    return this.requetes;
  }
}

