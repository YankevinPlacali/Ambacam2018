import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppConstant} from '../../../appConstant';
import {MotifSuppression} from '../../models/motif-suppression/motifSuppression';
import {Strings} from '../../utils/strings';
import {GlobalVariablesService} from '../global-variables/global-variables.service';


@Injectable()
export class MotifSuppressionService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) { }

  list() {
    return this._http.get(AppConstant.MOTIF_COLLECTION_PATH.toString(), this._authConstants.loadHeader());
  }

  create(motif: MotifSuppression) {
    return this._http.post(AppConstant.MOTIF_COLLECTION_PATH.toString(), motif, this._authConstants.loadHeader());
  }

  get(id: number) {
    return this._http.get(Strings.format(AppConstant.MOTIF_ITEM_PATH, id), this._authConstants.loadHeader());
  }

  update(id: number, motif: MotifSuppression) {
    return this._http.put(Strings.format(AppConstant.MOTIF_ITEM_PATH, id), motif, this._authConstants.loadHeader());
  }

  delete(id: number) {
    return this._http.delete(Strings.format(AppConstant.MOTIF_ITEM_PATH, id), this._authConstants.loadHeader());
  }

}
