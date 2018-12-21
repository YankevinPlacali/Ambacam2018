import {Injectable} from '@angular/core';
import {GlobalVariablesService} from '../../admin/services/global-variables/global-variables.service';
import {AppConstant} from '../../appConstant';
import {Action} from '../../admin/models/action/action';
import {HttpClient} from '@angular/common/http';
import {Strings} from '../../admin/utils/strings';
import {IdentifyRequerant} from '../models/identifyRequerant';

@Injectable()
export class ApplicantService {


  constructor(private _http: HttpClient) {
  }

  history(identifyRequerant: IdentifyRequerant) {
    return this._http.post(AppConstant.REQUETE_HISTORY_PATH, identifyRequerant);
  }
}
