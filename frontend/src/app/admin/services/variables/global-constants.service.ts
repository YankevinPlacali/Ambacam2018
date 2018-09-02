import {Injectable} from '@angular/core';
import {Auth} from '../../utils/auth/auth';

@Injectable()
export class GlobalConstantsService {

  constructor() {
  }

  getConnectedOperateur() {
    return Auth.getOperateur();
  }
}
