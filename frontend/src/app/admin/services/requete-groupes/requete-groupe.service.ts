import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppConstant} from '../../../appConstant';
import {RequeteGroupe} from '../../models/requete-groupe/requete-groupe';
import {Strings} from '../../utils/strings';
import {GlobalVariablesService} from '../global-variables/global-variables.service';
import {GlobalConstantsService} from '../variables/global-constants.service';
import {OperateurStd} from '../../models/operateur/operateurStd';

@Injectable()
export class RequeteGroupeService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) {
  }

  list(operateurId: number) {
    return this._http.get(Strings.format(AppConstant.REQUETE_GROUPE_COLLECTION_PATH, operateurId), this._authConstants.loadHeader());
  }

  create(requeteGroupe: RequeteGroupe, operateurId: number) {
    return this._http.post(Strings.format(AppConstant.REQUETE_GROUPE_COLLECTION_PATH, operateurId), requeteGroupe, this._authConstants.loadHeader());
  }

  get(id: number, operateurId: number) {
    return this._http.get(Strings.format(AppConstant.REQUETE_GROUPE_READ_ITEM_PATH, operateurId, id), this._authConstants.loadHeader());
  }

  update(id: number, requeteGroupe: RequeteGroupe, operateurId: number) {
    return this._http.put(Strings.format(AppConstant.REQUETE_GROUPE_READ_ITEM_PATH, operateurId, id), requeteGroupe, this._authConstants.loadHeader());
  }

  delete(id: number, operateurId: number) {
    return this._http.delete(Strings.format(AppConstant.REQUETE_GROUPE_READ_ITEM_PATH, operateurId, id), this._authConstants.loadHeader());
  }

  updateGroupe(requetesIds: any, requeteGroupeId: number, operateurId: number) {
    return this._http.put(Strings.format(AppConstant.REQUETE_GROUPE_ASSIGN_PATH, operateurId, requeteGroupeId), requetesIds, this._authConstants.loadHeader());
  }

  getRequetes(requeteGroupeId: number, operateurId: number) {
    return this._http.get(Strings.format(AppConstant.REQUETE_GROUPE_GET_REQUETES_PATH, operateurId, requeteGroupeId), this._authConstants.loadHeader());
  }

  updateGroupeStatus(statuId: any, requeteGroupeId: number, operateurId: number) {
    return this._http.put(Strings.format(AppConstant.REQUETE_GROUPE_UPDATE_STATUT_ITEM_PATH, operateurId, requeteGroupeId), statuId, this._authConstants.loadHeader());
  }


}
