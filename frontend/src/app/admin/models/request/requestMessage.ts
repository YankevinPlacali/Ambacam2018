export class RequestMessage {
  public type: string;
  public title: string;
  public message: string;
  public visibility: string;

  constructor(type: string, title: string, message: string, visibility: string) {
    this.type = type;
    this.title = title;
    this.message = message;
    this.visibility = visibility;
  }
}
