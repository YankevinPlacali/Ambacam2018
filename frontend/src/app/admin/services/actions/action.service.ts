import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppConstant} from '../../../appConstant';
import {Action} from '../../models/action/action';
import {Strings} from '../../utils/strings';
import {GlobalVariablesService} from '../global-variables/global-variables.service';

@Injectable()
export class ActionService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) {
  }

  list() {
    return this._http.get(AppConstant.ACTION_COLLECTION_PATH.toString(), this._authConstants.loadHeader());
  }

  create(role: Action) {
    return this._http.post(AppConstant.ACTION_COLLECTION_PATH.toString(), role, this._authConstants.loadHeader());
  }

  get(id: number) {
    return this._http.get(Strings.format(AppConstant.ACTION_ITEM_PATH, id), this._authConstants.loadHeader());
  }

  update(id: number, role: Action) {
    return this._http.put(Strings.format(AppConstant.ACTION_ITEM_PATH, id), role, this._authConstants.loadHeader());
  }

  delete(id: number) {
    return this._http.delete(Strings.format(AppConstant.ACTION_ITEM_PATH, id), this._authConstants.loadHeader());
  }

}
