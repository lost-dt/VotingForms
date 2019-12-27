import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

import { Question } from '../shared/question.model';
import { QuestionBase } from '../shared/question-base';
import { RadioButtonQuestion } from '../shared/question-radio';
import { SelectQuestion } from '../shared/question-select';
import { TextQuestion } from '../shared/question-text';

@Injectable({ providedIn: 'root' })
export class QuestionService {
  public questionControls = new BehaviorSubject<QuestionBase<any>[]>([]);

  private httpPostHeader =  new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor(private http: HttpClient) {}
  
  getQuestionControls(questionData: Question[]): void {
    const newQuestionControls = [];
    questionData.forEach(q => {
      switch (q.type) {
        case 'radio':
          newQuestionControls.push(new RadioButtonQuestion({
            key: `question${q.id}`,
            label: q.title,
            options: q.options.split(', '),
            required: true
          }));
          break;
        case 'select':
          newQuestionControls.push(new SelectQuestion({
            key: `question${q.id}`,
            label: q.title,
            options: q.options.split(', '),
            required: true
          }));
          break;
        case 'text':
          newQuestionControls.push(new TextQuestion({
            key: `question${q.id}`,
            label: q.title,
            required: true
          }));
      }
    });
    this.questionControls.next(newQuestionControls);
  }

  createQuestion(formHash: string, title: string, type: string, options: string): Observable<any> {
    return this.http.post('api/question',
                   JSON.stringify({ formHash, title, type, options }),
                   { headers: this.httpPostHeader, responseType: 'text' });
  }

  deleteQuestion(id: number): Observable<any> {
    return this.http.delete(`api/question/${id}`);
  }

  submitAnswers(answers: object) {
    const randId = Math.random() * 100000; // For debugging purposes
    Object.keys(answers).forEach(questionId => {
      this.http.post('api/vote',
                     JSON.stringify({ answer: Array.isArray(answers[questionId]) ? answers[questionId].join(', ') : answers[questionId],
                                      questionId: questionId.slice('question'.length),
                                      userId: randId }),
                     { headers: this.httpPostHeader, responseType: 'text' }).subscribe();
    });
  }
}
