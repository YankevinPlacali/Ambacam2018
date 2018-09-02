import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalVariablesService} from '../global-variables/global-variables.service';
import {Strings} from '../../utils/strings';
import {Role} from '../../models/role/role';
import {AppConstant} from '../../../appConstant';

@Injectable()
export class OperateurService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) {

  }

  get(id: number) {
    return this._http.get(Strings.format(AppConstant.OPERATEUR_ITEM_PATH, id), this._authConstants.loadHeader());
  }

  update(id: number, role: Role) {
    return this._http.put(Strings.format(AppConstant.OPERATEUR_ITEM_PATH, id), role, this._authConstants.loadHeader());
  }

}
