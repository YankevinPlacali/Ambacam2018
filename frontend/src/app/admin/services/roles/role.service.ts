import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppConstant} from '../../../appConstant';
import {Role} from '../../models/role/role';
import {Strings} from '../../utils/strings';

@Injectable()
export class RoleService {

  constructor(private _http: HttpClient) {
  }

  list() {
    return this._http.get(AppConstant.ROLE_COLLECTION_PATH.toString());
  }

  create(role: Role) {
    return this._http.post(AppConstant.ROLE_COLLECTION_PATH.toString(), role);
  }

  get(id: number) {
    return this._http.get(Strings.format(AppConstant.ROLE_ITEM_PATH, id));
  }

  update(id: number, role: Role) {
    return this._http.put(Strings.format(AppConstant.ROLE_ITEM_PATH, id), role);
  }

  delete(id: number) {
    return this._http.delete(Strings.format(AppConstant.ROLE_ITEM_PATH, id));
  }

}
