import {AuthResponseSuccess} from '../models/auth/authResponseSuccess';

export class ObjectToAuthResponse {

  public static authResponseSuccess: AuthResponseSuccess;

  public static applyOnSuccess(object): AuthResponseSuccess {
    this.authResponseSuccess = {
      accessToken: object.access_token,
      tokenType: object.token_type,
      expiresIn: object.expires_in,
      scope: object.scope
    };

    return this.authResponseSuccess;
  }
}
