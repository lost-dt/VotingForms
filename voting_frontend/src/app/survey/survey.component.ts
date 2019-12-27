import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';

import { Form } from '../shared/form.model';
import { FormService } from '../services/form.service';
import { QuestionBase } from '../shared/question-base';
import { QuestionService } from '../services/question.service';
import { QuestionControlService } from '../services/question-control.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-survey',
  templateUrl: './survey.component.html',
  styleUrls: ['./survey.component.css'],
  providers: [QuestionControlService]
})
export class SurveyComponent implements OnInit {
  public questions: QuestionBase<any>[] = [];
  public formGroup: FormGroup;
  public formData: Form;
  public isSubmitted = false;

  constructor(private qcs: QuestionControlService,
              private formService: FormService,
              private qs: QuestionService,
              private userService: UserService,
              private router: Router,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.formService.getFormByHash(params.get('formHash')).subscribe(formData => {
        this.formData = formData;
        this.qs.getQuestionControls(this.formData.questions);
        this.qs.questionControls.subscribe(questionControls => {
          if (questionControls.length) {
            this.questions = questionControls;
            this.formGroup = this.qcs.toFormGroup(questionControls);
          }
        });
      });
    });
  }

  navigateToStats(params?: object) {
    this.router.navigate(['stats', this.formData.hash], params);
  }

  onSubmit() {
    this.isSubmitted = true;
    this.userService.initUser(this.formData.hash);
    this.qs.submitAnswers(this.formGroup.value);
    this.snackBar.open('Thanks for participation!', 'Got it!', { duration: 7000 });
    this.navigateToStats({ queryParams: { disable: true } });
  }
}
