import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ReplaySubject, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { StatObject } from '../shared/stat-object.model';

@Injectable({ providedIn: 'root' })
export class StatisticsService {
  public statObject = new ReplaySubject<StatObject>();

  constructor(private http: HttpClient) {}

  getStatsByFormId(formHash: string): void {
    this.http.get<StatObject>(`api/vote/stats_${formHash}`)
      .pipe(catchError(this.handleError))
      .subscribe(statObject => {
        this.statObject.next(statObject);
      });
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
