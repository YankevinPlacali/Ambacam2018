import {Injectable} from '@angular/core';
import {AppConstant} from '../../../appConstant';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Strings} from '../../utils/strings';
import {ValueTO} from '../../models/valueTO';
import {Auth} from '../../utils/auth/auth';
import {GlobalVariablesService} from '../global-variables/global-variables.service';

@Injectable()
export class AuthServiceService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) {
  }

  authenticateAsOperateur(username: string, password: string) {

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + btoa(Strings.format('{0}:{1}', AppConstant.AUTH_USERNAME, AppConstant.AUTH_SECRET))
      })
    };

    return this._http.post(Strings.format(AppConstant.AUTH_AS_OPERATEUR_PATH, username, password), {}, httpOptions);
  }

  findOperateurByUsername(valueTO: ValueTO) {
    return this._http.post(AppConstant.OPERATEUR_ITEM_BY_USERNAME, valueTO, this._authConstants.loadHeader());
  }

  logout() {
    return this._http.delete(AppConstant.AUTH_REVOKE_PATH, this._authConstants.loadHeader());
  }
}
