import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalVariablesService} from '../global-variables/global-variables.service';
import {Strings} from '../../utils/strings';
import {AppConstant} from '../../../appConstant';
import {Requete} from '../../models/requete/requete';

@Injectable()
export class RequeteService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) {
  }

  list() {
    return this._http.get(AppConstant.REQUETE_COLLECTION_PATH.toString(), this._authConstants.loadHeader());
  }

  create(requete: Requete, operateurId: number) {
    return this._http.post(Strings.format(AppConstant.REQUETE_CREATE_ITEM_PATH.toString(), operateurId, requete.requerantId), requete, this._authConstants.loadHeader());
  }

  get(requete: Requete, operateurId: number) {
    return this._http.get(Strings.format(AppConstant.REQUETE_READ_ITEM_PATH, operateurId, requete.requerant.id, requete.id), this._authConstants.loadHeader());
  }

  update(requete: Requete, operateurId: number) {
    return this._http.put(Strings.format(AppConstant.REQUETE_READ_ITEM_PATH, operateurId, requete.requerantId, requete.id), requete, this._authConstants.loadHeader());
  }

  updateStatut(requete: Requete, operateurId: number) {
    return this._http.put(Strings.format(AppConstant.REQUETE_UPDATE_STATUT_ITEM_PATH, operateurId, requete.requerant.id, requete.id), requete, this._authConstants.loadHeader());
  }

  delete(requete, operateurId: number) {
    return this._http.delete(Strings.format(AppConstant.REQUETE_READ_ITEM_PATH, operateurId, requete.requerant.id, requete.id), this._authConstants.loadHeader());
  }

  updateStatusOfRequetes(requeteIds: any, requeteGroupeId: number, operateurId: number) {
    return this._http.put(Strings.format(AppConstant.REQUETE_UPDATE_STATUT_GROUPE_PATH, operateurId, requeteGroupeId), requeteIds, this._authConstants.loadHeader());
  }

}
