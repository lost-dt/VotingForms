import { Component, Input } from '@angular/core';

import { Form } from '../shared/form.model';
import { FormService } from '../services/form.service';
import { QuestionService } from '../services/question.service';

@Component({
  selector: 'app-form-list-item',
  templateUrl: './form-list-item.component.html',
  styleUrls: ['./form-list-item.component.css']
})
export class FormListItemComponent {
  @Input() form: Form;
  public columnsToDisplay = ['title', 'type', 'options', 'delete'];

  constructor(private formService: FormService,
              private questionService: QuestionService) {}

  updateForm() {
    this.formService.getFormByHash(this.form.hash).subscribe(form => this.form = form);
  }

  deleteForm() {
    this.formService.deleteForm(this.form.hash).subscribe(() => this.formService.getAllForms());
  }

  deleteQuestion(questionId: number) {
    this.questionService.deleteQuestion(questionId).subscribe(() => this.updateForm());
  }
}
