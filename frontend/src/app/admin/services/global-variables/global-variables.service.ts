import {Injectable} from '@angular/core';
import {Auth} from '../../utils/auth/auth';
import {HttpHeaders} from '@angular/common/http';

@Injectable()
export class GlobalVariablesService {

  constructor() {
  }

  loadHeader(): any {

    const token = Auth.retrieve('operateur.token');
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + token
      })
    };
    return httpOptions;
  }

}
