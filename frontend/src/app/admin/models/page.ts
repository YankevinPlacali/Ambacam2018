export class Page {
  public label: string;
  public page: number;
  public active: boolean;
  public disabled: boolean;

  constructor(label: string, page: number, active: boolean, disabled: boolean) {
    this.label = label;
    this.page = page;
    this.active = active;
    this.disabled = disabled;
  }
}
