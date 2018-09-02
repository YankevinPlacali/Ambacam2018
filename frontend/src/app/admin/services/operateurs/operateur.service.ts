import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalVariablesService} from '../global-variables/global-variables.service';
import {Strings} from '../../utils/strings';
import {Role} from '../../models/role/role';
import {AppConstant} from '../../../appConstant';
import {OperateurCreate} from '../../models/operateur/operateurCreate';

@Injectable()
export class OperateurService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) {

  }

  get(id: number) {
    return this._http.get(Strings.format(AppConstant.OPERATEUR_ITEM_PATH, id), this._authConstants.loadHeader());
  }

  create(operateurCreate: OperateurCreate) {
    return this._http.post(AppConstant.OPERATEUR_COLLECTION.toString(), operateurCreate, this._authConstants.loadHeader());
  }

  list() {
    return this._http.get(AppConstant.OPERATEUR_COLLECTION.toString(), this._authConstants.loadHeader());
  }

  update(id: number, operateur: OperateurCreate) {
    return this._http.put(Strings.format(AppConstant.OPERATEUR_ITEM_PATH, id), operateur, this._authConstants.loadHeader());
  }

  delete(id: number) {
    return this._http.delete(Strings.format(AppConstant.OPERATEUR_ITEM_PATH, id), this._authConstants.loadHeader());
  }

}
