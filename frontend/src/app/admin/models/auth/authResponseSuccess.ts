export class AuthResponseSuccess {
  public accessToken: string;
  public tokenType: string;
  public expiresIn: number;
  public scope: number;

  constructor(accessToken: string, tokenType: string, expiresIn: number, scope: number) {
    this.accessToken = accessToken;
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
    this.scope = scope;
  }
}
