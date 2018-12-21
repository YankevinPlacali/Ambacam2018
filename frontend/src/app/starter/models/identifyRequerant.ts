export class IdentifyRequerant {
  public identifier: string;
  public dateNaissance: Date;

  constructor(identifier: string, dateNaissance: Date) {
    this.identifier = identifier;
    this.dateNaissance = dateNaissance;
  }
}
