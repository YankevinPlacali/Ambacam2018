import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppConstant} from '../../../appConstant';
import {Pays} from '../../models/pays/pays';
import {Strings} from '../../utils/strings';
import {GlobalVariablesService} from '../global-variables/global-variables.service';

@Injectable()
export class PaysService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) {
  }

  list() {
    return this._http.get(AppConstant.PAYS_COLLECTION_PATH.toString(), this._authConstants.loadHeader());
  }

  create(role: Pays) {
    return this._http.post(AppConstant.PAYS_COLLECTION_PATH.toString(), role, this._authConstants.loadHeader());
  }

  get(id: number) {
    return this._http.get(Strings.format(AppConstant.PAYS_ITEM_PATH, id), this._authConstants.loadHeader());
  }

  update(id: number, role: Pays) {
    return this._http.put(Strings.format(AppConstant.PAYS_ITEM_PATH, id), role, this._authConstants.loadHeader());
  }

  delete(id: number) {
    return this._http.delete(Strings.format(AppConstant.PAYS_ITEM_PATH, id), this._authConstants.loadHeader());
  }

}
