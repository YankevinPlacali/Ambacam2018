import {Injectable} from '@angular/core';
import {GlobalVariablesService} from '../global-variables/global-variables.service';
import {HttpClient} from '@angular/common/http';
import {AppConstant} from '../../../appConstant';
import {Requete} from '../../models/requete/requete';
import {Strings} from '../../utils/strings';
import {PassportWrite} from '../../models/passport/passport';

@Injectable()
export class PassportService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) {
  }

  list(requeteId: number) {
    return this._http.get(Strings.format(AppConstant.PASSPORT_COLLECTION_PATH.toString(), 0, 0, requeteId), this._authConstants.loadHeader());
  }

  create(requeteId: number, passportWrite: PassportWrite) {
    return this._http.post(Strings.format(AppConstant.PASSPORT_COLLECTION_PATH.toString(), 0, 0, requeteId), passportWrite, this._authConstants.loadHeader());
  }

  get(passportId: number) {
    return this._http.get(Strings.format(AppConstant.PASSPORT_ITEM_PATH, 0, 0, 0, passportId), this._authConstants.loadHeader());
  }

  update(passportId: number, passportWrite: PassportWrite) {
    return this._http.put(Strings.format(AppConstant.PASSPORT_ITEM_PATH, 0, 0, 0, passportId), passportWrite, this._authConstants.loadHeader());
  }

  delete(passportId: number) {
    return this._http.delete(Strings.format(AppConstant.PASSPORT_ITEM_PATH, 0, 0, 0, passportId), this._authConstants.loadHeader());
  }
}
