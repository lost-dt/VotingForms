import { Component, EventEmitter, Input, Output } from '@angular/core';

import { LocalStorageService } from '../services/local-storage.service';
import { QuestionService } from '../services/question.service';

@Component({
  selector: 'app-question-form',
  templateUrl: './question-form.component.html',
  styleUrls: ['./question-form.component.css']
})
export class QuestionFormComponent {
  @Input() formHash: string;
  @Output() newQuestionAdded = new EventEmitter();
  inputTypes = ['text', 'radio', 'select'];
  type = 'text';
  title: string;
  options = '';

  constructor(private questionService: QuestionService) {}

  onSubmit() {
    if (this.type === 'text') {
      this.options = '';
    }
    this.questionService.createQuestion(this.formHash, this.title, this.type, this.options)
      .subscribe(() => this.newQuestionAdded.emit());
    LocalStorageService.clearSubmittedForm(this.formHash);
  }
}
