import { QuestionBase } from './question-base';

export class RadioButtonQuestion extends QuestionBase<string> {
  controlType = 'radio';

  constructor(options: {} = {}) {
    super(options);
  }
}
