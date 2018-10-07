import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppConstant} from '../../../appConstant';
import {Strings} from '../../utils/strings';
import {GlobalVariablesService} from '../global-variables/global-variables.service';
import {Autorite} from "../../models/autorite/autorite";

@Injectable()
export class AutoriteService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) {
  }

  list() {
    return this._http.get(AppConstant.AUTORITE_COLLECTION_PATH.toString(), this._authConstants.loadHeader());
  }

  create(autorite: Autorite) {
    return this._http.post(AppConstant.AUTORITE_COLLECTION_PATH.toString(), autorite, this._authConstants.loadHeader());
  }

  get(id: number) {
    return this._http.get(Strings.format(AppConstant.AUTORITE_ITEM_PATH, id), this._authConstants.loadHeader());
  }

  update(id: number, autorite: Autorite) {
    return this._http.put(Strings.format(AppConstant.AUTORITE_ITEM_PATH, id), autorite, this._authConstants.loadHeader());
  }

  delete(id: number) {
    return this._http.delete(Strings.format(AppConstant.AUTORITE_ITEM_PATH, id), this._authConstants.loadHeader());
  }

}

