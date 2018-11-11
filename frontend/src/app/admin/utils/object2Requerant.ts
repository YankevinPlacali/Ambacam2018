import {Requerant} from '../models/requerant/requerant';
import {DateUtils} from './dateUtils';
import {RequerantSearchResult} from '../models/requerant/requerantSearchResult';

export class Object2Requerant {
  public static requerant: Requerant;
  public static requerants: Requerant [];
  public static requerantSearchResult: RequerantSearchResult;

  constructor() {

  }

  public static apply(object): Requerant {
    this.requerant = {
      id: object.id,
      nom: object.nom,
      prenom: object.prenom,
      dateNaissance: DateUtils.formatDateFromTimestamp(object.dateNaissance),
      creatorId: object.creatorId,
      creeLe: DateUtils.formatDateFromTimestamp(object.creeLe),
      sexe: object.sexe,
      profession: object.profession,
      lieuNaissance: object.lieuNaissance,
      nationalite: object.nationalite,
      paysId: -1,
    };

    return this.requerant;
  }

  public static applyOnArray(objects): Requerant[] {
    this.requerants = [];
    objects.forEach(object => {
      this.requerants.push(this.apply(object));
    });

    return this.requerants;
  }

  public static applyOnSearchResult(object): RequerantSearchResult {

    const content: Requerant[] = [];
    object.content.forEach(requerant => {
      content.push(this.apply(requerant));
    });
    this.requerantSearchResult = {
      page: object.page,
      totalPages: object.totalPages,
      content: content
    };

    return this.requerantSearchResult;
  }
}
