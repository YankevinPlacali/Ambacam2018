import { Injectable } from '@angular/core';
import {Requete} from '../../models/requete/requete';
import {AppConstant} from '../../../appConstant';
import {Strings} from '../../utils/strings';
import {GlobalVariablesService} from '../global-variables/global-variables.service';
import {HttpClient} from '@angular/common/http';
import {Recepisse} from '../../models/recepisse/recepisse';

@Injectable()
export class RecepisseService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) { }

  list() {
    return this._http.get(AppConstant.RECEPISSE_COLLECTION_PATH.toString(), this._authConstants.loadHeader());
  }

  create(recepisse: Recepisse) {
    return this._http.post(Strings.format(AppConstant.RECEPISSE_COLLECTION_PATH.toString(), recepisse.recepisseNo), recepisse, this._authConstants.loadHeader());
  }

  get(recepisse: Recepisse) {
    return this._http.get(Strings.format(AppConstant.RECEPISSE_ITEM_PATH, recepisse.requerantId, recepisse.recepisseNo), this._authConstants.loadHeader());
  }

  update(recepisse: Recepisse) {
    return this._http.put(Strings.format(AppConstant.RECEPISSE_ITEM_PATH, recepisse.recepisseNo), recepisse, this._authConstants.loadHeader());
  }

  delete(recepisse: Recepisse) {
    return this._http.delete(Strings.format(AppConstant.RECEPISSE_ITEM_PATH, recepisse.id, recepisse.requerantId, recepisse.requeteId), this._authConstants.loadHeader());
  }

}
