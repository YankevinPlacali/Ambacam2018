import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalVariablesService} from '../global-variables/global-variables.service';
import {Strings} from '../../utils/strings';
import {Statut} from '../../models/statut/statut';
import {AppConstant} from '../../../appConstant';

@Injectable()
export class StatutService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) { }

  list() {
    return this._http.get(AppConstant.STATUT_COLLECTION_PATH.toString(), this._authConstants.loadHeader());
  }

  create(statut: Statut) {
    return this._http.post(AppConstant.STATUT_COLLECTION_PATH.toString(), statut, this._authConstants.loadHeader());
  }

  get(id: number) {
    return this._http.get(Strings.format(AppConstant.STATUT_ITEM_PATH, id), this._authConstants.loadHeader());
  }

  update(id: number, statut: Statut) {
    return this._http.put(Strings.format(AppConstant.STATUT_ITEM_PATH, id), statut, this._authConstants.loadHeader());
  }

  delete(id: number) {
    return this._http.delete(Strings.format(AppConstant.STATUT_ITEM_PATH, id), this._authConstants.loadHeader());
  }
}
