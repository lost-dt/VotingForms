import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';

import { Form } from '../shared/form.model';

@Injectable({ providedIn: 'root' })
export class FormService {

  public forms = new BehaviorSubject<Form[]>([]);

  private httpPostHeader =  new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor(private http: HttpClient) {}

  getAllForms(): void {
    this.http.get<Form[]>('api/form/data_all').pipe(catchError(this.handleError)).subscribe(forms => {
      this.forms.next(forms);
    });
  }

  getFormByHash(formHash: string): Observable<Form> {
    return this.http.get<Form>(`api/form/data_${formHash}`).pipe(catchError(this.handleError));
  }

  createForm(title: string): Observable<any> {
    return this.http.post('api/form',
                          JSON.stringify({ title }),
                          { headers: this.httpPostHeader, responseType: 'text' });
  }

  deleteForm(formHash: string): Observable<any> {
    return this.http.delete(`api/form/hash_${formHash}`);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError(
      'Something bad happened; please try again later.');
  }
}
