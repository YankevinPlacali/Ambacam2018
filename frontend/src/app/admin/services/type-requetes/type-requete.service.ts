import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppConstant} from '../../../appConstant';
import {TypeRequete} from '../../models/type-requete/type-requete';
import {Strings} from '../../utils/strings';
import {GlobalVariablesService} from '../global-variables/global-variables.service';

@Injectable()
export class TypeRequeteService {

  constructor(private _http: HttpClient, private _authConstants: GlobalVariablesService) {
  }

  list() {
    return this._http.get(AppConstant.TYPE_REQUETES_COLLECTION_PATH.toString(), this._authConstants.loadHeader());
  }

  create(typeRequete: TypeRequete) {
    return this._http.post(AppConstant.TYPE_REQUETES_COLLECTION_PATH.toString(), typeRequete, this._authConstants.loadHeader());
  }

  get(id: number) {
    return this._http.get(Strings.format(AppConstant.TYPE_REQUETES_ITEM_PATH, id), this._authConstants.loadHeader());
  }

  update(id: number, typeRequete: TypeRequete) {
    return this._http.put(Strings.format(AppConstant.TYPE_REQUETES_ITEM_PATH, id), typeRequete, this._authConstants.loadHeader());
  }

  delete(id: number) {
    return this._http.delete(Strings.format(AppConstant.TYPE_REQUETES_ITEM_PATH, id), this._authConstants.loadHeader());
  }

}
