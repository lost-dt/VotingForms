import { Component, OnInit } from '@angular/core';

import { Form } from '../shared/form.model';
import { FormService } from '../services/form.service';

@Component({
  selector: 'app-question-list',
  templateUrl: './form-list.component.html',
  styleUrls: ['./form-list.component.css']
})
export class FormListComponent implements OnInit {
  public forms: Form[] = [];

  constructor(private formService: FormService) {}

  ngOnInit() {
    this.formService.getAllForms();
    this.formService.forms.subscribe(forms => {
      this.forms = forms;
    });
  }
}
