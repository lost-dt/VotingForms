import { Question } from './question.model';

export class Form {
  constructor(public hash: string, public title: string, public questions: Question[]) {}
}
