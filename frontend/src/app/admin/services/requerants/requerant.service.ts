import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalVariablesService} from '../global-variables/global-variables.service';
import {Strings} from '../../utils/strings';
import {AppConstant} from '../../../appConstant';
import {Requerant} from "../../models/requerant/requerant";

@Injectable()
export class RequerantService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) {
  }

  list() {
    return this._http.get(AppConstant.REQUERANT_COLLECTION_PATH.toString(), this._authConstants.loadHeader());
  }

  create(requerant: Requerant) {
    return this._http.post(AppConstant.REQUERANT_COLLECTION_PATH.toString(), requerant, this._authConstants.loadHeader());
  }

  get(id: number) {
    return this._http.get(Strings.format(AppConstant.REQUERANT_ITEM_PATH, id), this._authConstants.loadHeader());
  }

  update(id: number, requerant: Requerant) {
    return this._http.put(Strings.format(AppConstant.REQUERANT_ITEM_PATH, id), requerant, this._authConstants.loadHeader());
  }

  delete(id: number) {
    return this._http.delete(Strings.format(AppConstant.REQUERANT_ITEM_PATH, id), this._authConstants.loadHeader());
  }

}
