export class Question {
  public id: number;
  public formId: string;
  public title: string;
  public type: string;
  public options: string;
  constructor(formId: string, title: string, type: string, options: string) {
    this.formId = formId;
    this.title = title;
    this.type = type;
    this.options = options;
  }
}
